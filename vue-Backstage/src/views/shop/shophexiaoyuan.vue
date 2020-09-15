<template>
  <div class="app-container">
    <div>
      <el-button type="success" size="small" class="form-btn-diy" @click="dialogFormVisible=true">添加核销员</el-button>
    </div>
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      height="500"
      border
      stripe
      fit
      highlight-current-row
    >
      <el-table-column label="编号"  align="center">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>
      <el-table-column label="商家名称"  align="center">
        <template slot-scope="scope">
          {{ scope.row.shopname }}
        </template>
      </el-table-column>
      <el-table-column label="核销人"  align="center">
        <template slot-scope="scope">
          {{ scope.row.userphone }}
        </template>
      </el-table-column> 
   
      <el-table-column label="操作"  width="200">
          <template slot-scope="scope">
            <el-button type="danger"  @click.native.prevent="doDel(scope.row.id)">删除</el-button> 
          </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <div class="b_pagebox">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="listSize"
        @current-change="currentChange"
        >
      </el-pagination>
    </div>
    <el-dialog title="添加核销员" @close="guanbi" :visible.sync="dialogFormVisible" width="40%" :close-on-click-modal="false" >
      <el-form :model="form" label-width="100px" >
       
         <el-form-item label="商户名称">
              <el-select v-model="form.shopid" placeholder="请选择商户名称">
                <el-option :key="item.id" v-for="item in typeList" :label='item.shopname' :value="item.id"></el-option>
              </el-select>
        </el-form-item>
             <el-form-item label="核销员电话">
          <el-input v-model="form.userphone" class="from_i_12"></el-input>
          <div class="red-ts">请输入25个字符以内</div>
        </el-form-item>
           </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="onSubmit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
 import {getHxList,dataHxSub,dataDetail,dataHxDel,getType} from '@/api/shop'

export default {
   data() {
    return {
      time:null,
      time2:null,
      dialogFormVisible: false,
      form:{
       
      },
      list: null,
      listLoading: true,
      listSize:0,
      typeList:null,
      select:{
          offset: 1, /*分页参数： 当前页码  默认=1*/
          limmit: 10 /*分页参数： 当前页数据条目数 默认=10*/
      }
    }
  },
  created() {
    this.currentChange(1)
    this.getType()
  },
  methods: {
   
    guanbi(){
      console.log("关闭")
      this.time=null,
      this.time2=null,
      this.form = {
        path:null,
        infoPath:null
      }
    },
    onSubmit(){
      console.log(this.form)
      
      dataHxSub(this.form).then(res => {
          if(res.status === 10000){
              this.$message.success('添加成功');
              this.dialogFormVisible = false
              //刷新数据
              this.currentChange(1)
            }else if(res.status === 20000){
              this.$message.error(res.message);
          }
      })
    },
    getType() {
      getType().then(res => {
          if(res.status === 10000){
            this.typeList = res.data
            }else if(res.status === 20000){
            this.$message.error(res.message);
          }
      })
    },
    doDel(id){
      //是否删除
        this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          dataHxDel(id).then(res => {
              if(res.status === 10000){
                    //刷新数据
                  this.currentChange(1)
                  this.$message({
                    type: 'success',
                    message: '删除成功!'
                  });
                }else if(res.status === 20000){
                  this.$message.error(res.message);
              }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
    },
    toEdit(id){
      dataDetail(id).then(res => {
          if(res.status === 10000){
              this.form = res.data
              this.form.parentId = this.form.parentId.toString()
              this.time = [null,null]
              this.time2 = [null,null]
              this.time[0] = this.form.startTime 
              this.time[1] = this.form.endTime
              this.time2[0] = this.form.enrollStartTime 
              this.time2[1] = this.form.enrollEndTime
              this.dialogFormVisible = true
            }else if(res.status === 20000){
              this.$message.error(res.message);
          }
      })
    },
    currentChange(e){
        this.listLoading = true
        this.select.offset = e
        getHxList(this.select).then(res => {
            if(res.status === 10000){
              this.listLoading = false
              this.list = res.data.list
              this.listSize = res.data.total
            }else if(res.status === 20000){
              this.$message.error(res.message);
            }
        })
    },
     
    
  }
}
</script>
