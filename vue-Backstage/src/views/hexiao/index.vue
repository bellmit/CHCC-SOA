<template>
  <div class="app-container">
    <div>
        <el-form :inline="true" :model="select" class="searchForm">
          <el-form-item label="请输入微信昵称">
            <el-input
              v-model="select.name"
              class="seach"
              placeholder="请输入微信昵称"
              size="small"
              clearable/>
          </el-form-item>
          <el-form-item label="请输入手机号">
            <el-input
              v-model="select.mobile"
              class="seach"
              placeholder="请输入手机号"
              maxlength="11"
              size="small"
              clearable/>
          </el-form-item>
          <el-form-item label="请输入身份证号">
            <el-input
              v-model="select.idCard"
              class="seach"
              placeholder="请输入身份证号"
              size="small"
              clearable/>
          </el-form-item>
          <div class="seachBtn">
            <el-button icon="el-icon-search" @click="currentChange(1)" circle/>
            <el-button icon="el-icon-refresh" @click="restSearch" circle/>
          </div>
        </el-form>
    </div>
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
      <el-table-column label="微信昵称"  align="center">
        <template slot-scope="scope">
          {{ scope.row.nickname }}
        </template>
      </el-table-column>
      <el-table-column label="微信头像"  align="center" class-name="b_imgBox">
        <template slot-scope="scope">
          <img :src="scope.row.imgPath"  onerror="javascript:this.src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560835537905&di=fefc36c4df18783fc167340937ee13e4&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201607%2F27%2F20160727132507_yP8fe.jpeg'"  alt="">
        </template>
      </el-table-column>
      <el-table-column label="客户姓名"  align="center">
        <template slot-scope="scope">
          {{ scope.row.name }}
        </template>
      </el-table-column>
      <el-table-column label="验证手机号"  align="center">
        <template slot-scope="scope">
          {{ scope.row.mobile }}
        </template>
      </el-table-column>
      <el-table-column label="身份证号码"  align="center">
        <template slot-scope="scope">
          {{ scope.row.idCard }}
        </template>
      </el-table-column>
        <el-table-column label="操作"  width="200">
          <template slot-scope="scope">
            <el-button type="primary"  @click.native.prevent="toEdit(scope.row.id)">编辑</el-button> 
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
    <el-dialog title="身份编辑" @close="guanbi" :visible.sync="dialogFormVisible" width="40%" :close-on-click-modal="false" >
      <el-form :model="form" label-width="120px" >
        <el-form-item label="客户姓名">
             <el-input v-model="form.name" class="from_i_12"></el-input>
        </el-form-item>
        <el-form-item label="验证手机号">
             <el-input v-model="form.mobile" class="from_i_12"></el-input>
        </el-form-item>
        <el-form-item label="身份证号码">
             <el-input v-model="form.idCard" class="from_i_12"></el-input>
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
import {upload_path} from '@/api/api'
import {getList,dataSub,dataDetail,dataDel2} from '@/api/member'

export default {
  data() {
    return {
      dialogVisible: false,
      paths:null,
      dialogFormVisible: false,
      form: {
        id: null,
        name: null,
        mobile: null,
        idCard: null,
      },
      uploadPath:upload_path,
      list: null,
      listLoading: true,
      listSize:0,
      select:{
          type:2,
          offset: 1, /*分页参数： 当前页码  默认=1*/
          limmit: 10, /*分页参数： 当前页数据条目数 默认=10*/
          dateTime:null
      },
      pathsLoad: [],
    }
  },
  created() {
    this.currentChange(1)
  },
  methods: {
    restSearch() {
      this.select = {
          type:2,
          offset:1,
          limmit:10,
          dateTime:null
      }
      this.currentChange(1)
    },
    guanbi(){
      this.form = {
        id: null,
        name: null,
        mobile: null,
        idCard: null,
      }
    },
    onSubmit(){
      if(this.paths){
         this.form.paths = this.paths.join(",")
      }
      dataSub(this.form).then(res => {
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
    doDel(id){
      //是否删除
        this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          dataDel2(id).then(res => {
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
              if(res.data.paths){
                res.data.paths.forEach(data => {
                    this.paths.push(data.dataPath)
                    this.pathsLoad.push({url: data.dataPath,upUrl:data.dataPath})
                })
              }
              this.dialogFormVisible = true
            }else if(res.status === 20000){
              this.$message.error(res.message);
          }
      })
    },
    currentChange(e){
        this.listLoading = true
        this.select.offset = e
        console.log(this.select)
        getList(this.select).then(res => {
            if(res.status === 10000){
              this.listLoading = false
              this.list = res.data.list
              this.listSize = res.data.total
            }else if(res.status === 20000){
              this.$message.error(res.message);
            }
        })
    },
    handleAvatarSuccess(res, file){
      console.log(res)
      console.log(file)
      this.$message.success("上传成功");
    },
    //excel 上传
    beforeUpload (file) {

    },
    // 上传文件
    uploadFile (item) {
      console.log(item)
      const fileObj = item.file
      // FormData 对象
      const form = new FormData()
      // 文件对象
      form.append('file', fileObj)
      console.log(JSON.stringify(form))
      // let formTwo = JSON.stringify(form)

    }
    
  }
}
</script>
