<template>
  <div class="app-container">
    <div>
    <el-form :inline="true" :model="select" class="searchForm">
        <el-form-item label="请选择日期">
          <el-date-picker
            v-model="select.dateTime"
            type="date"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
      <div class="seachBtn">
        <el-button icon="el-icon-search" @click="currentChange(1)" circle/>
        <el-button icon="el-icon-refresh" @click="restSearch" circle/>
      </div>
    </el-form>
      <el-button type="success" size="small" class="form-btn-diy" @click="dialogFormVisible=true">添加</el-button>
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
       <el-table-column label="时间"  align="center">
        <template slot-scope="scope">
          {{ scope.row.date }}
        </template>
      </el-table-column>
      <el-table-column label="活动标题"  align="center">
        <template slot-scope="scope">
          {{ scope.row.title }}
        </template>
      </el-table-column>
      <el-table-column label="图片展示" align="center" class-name="b_imgBox" width="150">
        多图展示
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
    <el-dialog title="精彩回顾" @close="guanbi" :visible.sync="dialogFormVisible" width="40%" :close-on-click-modal="false" >
      <el-form :model="form" label-width="120px" >
        <el-form-item label="请选择日期:">
          <el-date-picker
            v-model="form.date"
            type="date"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="请输入标题:">
          <el-input v-model="form.title" class="from_i_12"></el-input>
          <div class="red-ts">请输入25个字符以内</div>
        </el-form-item>
        <el-form-item label="请选择图片:">
          <el-upload
            :action="uploadPath"
            list-type="picture-card"
            :file-list="pathsLoad"
            :on-success="handleAvatarSuccess"
            :on-remove="handleRemove"
            :before-upload="beforeAvatarUpload"
             multiple>
            <i class="el-icon-plus"></i>
            <div class="el-upload__tip" slot="tip" style="color: #e85555;">只能上传jpg/png文件，且不超过1M,图片宽高比(3:2)</div>
          </el-upload>
          <!-- <el-dialog v-model="dialogVisible" size="tiny">
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog> -->
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
import {getList,dataSub,dataDetail,dataDel} from '@/api/huigu'

export default {
  data() {
    return {
      dialogVisible: false,
      paths:[],
      dialogFormVisible: false,
      form: {
        id: null,
        title:null,
        date: null,
        paths: null,
      },
      uploadPath:upload_path,
      list: null,
      listLoading: true,
      listSize:0,
      select:{
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
          offset:1,
          limmit:10,
          dateTime:null
      }
      this.paths =[]
      this.currentChange(1)
    },
    guanbi(){
      this.form = {
        id: null,
        title:null,
        sendUrl: null,
        path: null,
      }
      this.paths = []
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
              this.paths =[]
              this.pathsLoad =[]
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
          dataDel(id).then(res => {
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
              console.log(res.data)
              if(res.data.paths){
                var data =  res.data.paths.split(',')
                console.log(333)
                console.log(data)
                this.paths = []
                this.pathsLoad = []
                data.forEach(data => {
                    this.paths.push(data)
                    this.pathsLoad.push({url: data,upUrl:data})
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
    //图片
    handleRemove(file,fileList) {
      const array = []
      fileList.forEach(data => {
        array.push(data.upUrl)
      })
      console.log('移除图片')
      console.log(array)
      this.paths = array;
    },
    handleAvatarSuccess(res, file,fileList) {
      if(res.status === 10000){
        this.paths.push(res.data)
      }else if(res.status === 20000){
        this.$message.error(res.message);
        fileList.pop()
      }
    },
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 5;
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 5MB!');
      }
      return isLt2M;
    },

    
  }
}
</script>
