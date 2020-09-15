<template>
  <div class="app-container">
    <div>
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
      <el-table-column label="图片分类"  align="center">
        <template slot-scope="scope">
          {{ scope.row.type==1?"首页":""}}
          {{ scope.row.type==2?"归心活动":""}}
          {{ scope.row.type==3?"专属权益":""}}
          {{ scope.row.type==4?"广告1":""}}
          {{ scope.row.type==5?"广告2":""}}
 
        </template>
      </el-table-column>
      <el-table-column label="图片" align="center" class-name="b_imgBox" width="150">
        <template slot-scope="scope">
          <img :src="scope.row.path" onerror="javascript:this.src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560835537905&di=fefc36c4df18783fc167340937ee13e4&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201607%2F27%2F20160727132507_yP8fe.jpeg'"  alt="">
        </template>
      </el-table-column>

      <el-table-column label="跳转链接"  align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.sendUrl }}</span>
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
    <el-dialog title="轮播编辑" @close="guanbi" :visible.sync="dialogFormVisible" width="40%" :close-on-click-modal="false" >
      <el-form :model="form" label-width="80px" >
        <el-form-item label="分类标题">
              <el-select v-model="form.type" placeholder="请选择活动分类">
                <el-option :key="item.label" v-for="item in typeData" :label='item.label' :value="item.value"></el-option>
                <!-- <el-option  label='归心活动' value="2"></el-option>
                <el-option  label='专属权益' value="3"></el-option>
                <el-option  label='广告1' value="4"></el-option>
                <el-option  label='广告2' value="5"></el-option> -->
              </el-select>
        </el-form-item>
        <el-form-item label="图片:">
          <el-upload
            class="avatar-uploader avatar-uploader-left"
            :action="uploadPath"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="form.path" :src="form.path" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <div class="upload-bottom-ts">只能上传jpg/png文件，且不超过1M，banner图片宽高比(2:1)广告图片宽高比(4.5:1)</div>
        </el-form-item>
        <el-form-item label="跳转链接">
          <el-input v-model="form.sendUrl" class="from_i_12"></el-input>
          <div class="upload-bottom-ts">跳转小程序页面请输入/pages/detail/detail?id=xxx(xxx是活动管理下活动列表里面的编号)例如/pages/detail/detail?id=18</div>
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
import {getList,dataSub,dataDetail,dataDel} from '@/api/banner'

export default {
  data() {
    return {
      typeData: [{
        value: 1,
        label: '首页'
      }, {
        value: 2,
        label: '归心活动'
      }, {
        value: 3,
        label: '专属权益'
      }, {
        value: 4,
        label: '广告1'
      }, {
        value: 5,
        label: '广告2'
      }],
      dialogFormVisible: false,
      form: {
        id: null,
        type:null,
        sendUrl: null,
        path: null,
      },
      uploadPath:upload_path,
      list: null,
      listLoading: true,
      listSize:0,
      select:{
          offset: 1, /*分页参数： 当前页码  默认=1*/
          limmit: 10 /*分页参数： 当前页数据条目数 默认=10*/
      }
    }
  },
  created() {
    this.currentChange(1)
  },
  methods: {
    guanbi(){
      this.form = {
        id: null,
        type:null,
        sendUrl: null,
        path: null,
      }
    },
    onSubmit(){
      console.log(this.form)
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
              this.dialogFormVisible = true
            }else if(res.status === 20000){
              this.$message.error(res.message);
          }
      })
    },
    currentChange(e){
        this.listLoading = true
        this.select.offset = e
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
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 5;
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 5MB!');
      }
      return isLt2M;
    },
    //图片上传成功
    handleAvatarSuccess(res, file,fileList) {
      if(res.status === 10000){
        console.log(res.data)
        console.log(this.form)
        this.form.path=res.data
      }else if(res.status === 20000){
        this.$message.error(res.message);
        fileList.pop()
      }
    },

    
  }
}
</script>
