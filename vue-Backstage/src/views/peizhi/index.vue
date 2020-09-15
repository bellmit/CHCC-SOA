<template>
  <div class="app-container">
    <div class="b_topbox">
       <el-button size="small" type="primary" @click="addNew()">添加配置</el-button>
    </div>
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      width="60%"
      :before-close="handleClose">
      <el-form :model="subForm" v-if="footerShow">
        <el-form-item label="配置名称" :label-width="formLabelWidth" >
          <el-input v-model="subForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <div id="sbzt">
        <el-form-item label="图片" :label-width="formLabelWidth" >
          <el-upload
            class="avatar-uploader"
            :action="uploadPath"
            :show-file-list="false"
            :on-success="fetchData"
            >
             <img v-if="subForm.imgPath" :src="subForm.imgPath" class="avatar">
             <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        </div>
        <el-form-item label="类型" :label-width="formLabelWidth" style="line-h">
          <el-select v-model="subForm.code" placeholder="选择分类">
            <el-option
              v-for="item in typeList"
              :key="item.code"
              :label="item.label"
              :value="item.code">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="显示名称" :label-width="formLabelWidth" >
          <el-input type="textarea" v-model="subForm.label" autocomplete="off"></el-input>
        </el-form-item>
       <el-form-item label="内容" :label-width="formLabelWidth" class="fwenben">
          <el-input type="textarea" v-model="subForm.content" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer" v-if="footerShow">
        <el-button @click="resetForm('subForm')">取 消</el-button>
        <el-button type="primary" @click="doArticleCommit()">确 定</el-button>
      </span>
    </el-dialog>
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
      <el-table-column label="显示名称" align="center" class-name="b_imgBox" width="150">
        <template slot-scope="scope">
          {{ scope.row.label }}
        </template>
      </el-table-column>
      <el-table-column label="配置名称"  align="center">
        <template slot-scope="scope">
          {{ scope.row.name }}
        </template>
      </el-table-column>
      <el-table-column label="内容"  align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.content }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态"  align="center">
        <template slot-scope="scope">
          {{ scope.row.status }}
        </template>
      </el-table-column>
      <el-table-column label="父级名称"  align="center">
        <template slot-scope="scope">
          {{ scope.row.parentName }}
        </template>
      </el-table-column>
      <el-table-column label="类型"  align="center">
        <template slot-scope="scope">
          {{ scope.row.type }}
        </template>
      </el-table-column>
        <el-table-column
        fixed="right"
        label="操作"
        width="100"
        class-name="b_btnbox"
       >
        <template slot-scope="scope">
          <el-button type="primary"  @click.native.prevent="doChange(scope.row.id)">编辑</el-button> 
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
  </div>
</template>

<script>
import {upload_path} from '@/api/api'
import {articleTypes,articleList,articleDetail,articleCommit} from '@/api/peizhi'
export default {
  data() {
    return {
      footerShow:true,
      menpiaoeditor:'',
      formLabelWidth: '120px',
      subForm:{
         code:'',
         text:''
      },
      title:'',
      dialogVisible: false,
      uploadPath:upload_path,
      list: null,
      typeList:null,
      listLoading: true,
      listSize:0,
      select:{
          offset: 1, /*分页参数： 当前页码  默认=1*/
          limmit: 10 /*分页参数： 当前页数据条目数 默认=10*/
      }
    }
  },
  created() {
    this.getType()
    this.getListData()
  },
  methods: {
    beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
    },
    //图片回显数据
    fetchData(e){
      this.subForm.imgPath = e.data
      console.log(this.subForm.imgPath)
    },
    //获取富文本内容
    geteditor(data){
      this.subForm.text = data
    },
    addNew(){
      this.menpiaoeditor = ''
      this.subForm = {
         code:'',
         text:''
      }
      this.showTable('新增配置',true)
    },
    //显示弹窗
    showTable(t,flag){
      this.footerShow = flag
      this.dialogVisible = true
      this.title = t
    },
    //修改
    doChange(id){
        this.getDetail(id,true)
    },
    //关闭弹出层
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.resetForm('subForm')
          done();
        })
        .catch(_ => {});
    },
    getType(){
      articleTypes().then(res => {
          if(res.status === 10000){
            this.typeList = res.data
            }else if(res.status === 20000){
            this.$message.error(res.message);
          }
      })
    },
    getListData() {
      this.listLoading = true
      articleList(this.select).then(res => {
          if(res.status === 10000){
            this.listLoading = false
            this.list = res.data.list
            this.listSize = res.data.total
            }else if(res.status === 20000){
            this.$message.error(res.message);
          }
      })
    },
    currentChange(e){
        this.listLoading = true
        this.select.offset = e
        articleList(this.select).then(res => {
            if(res.status === 10000){
              this.listLoading = false
              this.list = res.data.list
              this.listSize = res.data.total
            }else if(res.status === 20000){
              this.$message.error(res.message);
            }
        })
    },
    //获取文章列表
    getDetail(id,flag){
      articleDetail(id).then(res => {
          if(res.status === 10000){
             this.showTable('文章详情',flag)
             console.log(res)
             this.subForm =  res.data
            }else if(res.status === 20000){
            this.$message.error(res.message);
          }
      })
    },
    doArticleCommit(){
        articleCommit(this.subForm).then(res => {
          if(res.status === 10000){
              this.dialogVisible = false
              this.$message.success('添加成功');
              this.select.offset = 1
              this.getListData()
            }else if(res.status === 20000){
            this.$message.error(res.message);
          }
        })
    },
    //重置表单
    resetForm(formName){
      this.dialogVisible = false
    },
   
  }
}
</script>
<style>
  .wzzt{
    width: 150px; height: 150px;
  }
  #sbzt .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
   #sbzt .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
   #sbzt .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
   #sbzt .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
