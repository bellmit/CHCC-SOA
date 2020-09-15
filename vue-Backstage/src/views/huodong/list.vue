<template>
  <div class="app-container">
    <div>
      <el-button type="success" size="small" class="form-btn-diy" @click="dialogFormVisible=true">添加活动</el-button>
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
      <el-table-column label="分类标题"  align="center">
        <template slot-scope="scope">
          {{ scope.row.parentName }}
        </template>
      </el-table-column>
      <el-table-column label="活动标题"  align="center">
        <template slot-scope="scope">
          {{ scope.row.name }}
        </template>
      </el-table-column>
      <el-table-column label="活动图片" align="center" class-name="b_imgBox" width="150">
        <template slot-scope="scope">
          <img :src="scope.row.path" onerror="javascript:this.src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560835537905&di=fefc36c4df18783fc167340937ee13e4&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201607%2F27%2F20160727132507_yP8fe.jpeg'"  alt="">
        </template>
      </el-table-column>
      <el-table-column label="活动时间"  align="center">
        <template slot-scope="scope">
          {{ scope.row.startTime }}-{{ scope.row.endTime }}
        </template>
      </el-table-column>
      <el-table-column label="报名时间"  align="center">
        <template slot-scope="scope">
          {{ scope.row.enrollStartTime }}-{{ scope.row.enrollEndTime }}
        </template>
      </el-table-column>
      <el-table-column label="报名地点"  align="center">
        <template slot-scope="scope">
          {{ scope.row.location }}
        </template>
      </el-table-column>
      <el-table-column label="人数限制"  align="center">
        <template slot-scope="scope">
          {{ scope.row.limitCount }}
        </template>
      </el-table-column>
      <el-table-column label="账号报名数"  align="center">
        <template slot-scope="scope">
          {{ scope.row.memberCount }}
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
    <el-dialog title="修改活动" @close="guanbi" :visible.sync="dialogFormVisible" width="40%" :close-on-click-modal="false" >
      <el-form :model="form" label-width="100px" >
        <el-form-item label="分类标题">
              <el-select v-model="form.parentId" placeholder="请选择活动分类">
                <el-option :key="item.label" v-for="item in typeList" :label='item.label' :value="item.code"></el-option>
              </el-select>
        </el-form-item>
        <el-form-item label="活动标题">
          <el-input v-model="form.name" class="from_i_12"></el-input>
          <div class="red-ts">请输入25个字符以内</div>
        </el-form-item>
        <el-form-item label="活动时间">
          <el-date-picker
            v-model="time"
            type="datetimerange"
            start-placeholder="开始日期"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
            end-placeholder="结束日期"
            :default-time="['00:00:00']">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="报名时间">
          <el-date-picker
            v-model="time2"
            type="datetimerange"
            start-placeholder="开始日期"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
            end-placeholder="结束日期"
            :default-time="['00:00:00']">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="活动地点">
          <el-input v-model="form.location" class="from_i_12"></el-input>
          <div class="red-ts">请输入30个字符以内</div>
        </el-form-item>
        <el-form-item label="人数限制">
          <el-input-number v-model="form.limitCount"  :min="1" :max="1000000" ></el-input-number>
        </el-form-item>
        <el-form-item label="账号报名数">
          <el-input-number v-model="form.memberCount"  :min="1" :max="1000000" ></el-input-number>
        </el-form-item>
        <el-form-item label="活动图片">
          <el-upload
            class="avatar-uploader avatar-uploader-left"
            :action="uploadPath"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="form.path" :src="form.path" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <div class="upload-bottom-ts">只能上传jpg/png文件，且不超过1M，宽高比（1:1）</div>
        </el-form-item>
        <el-form-item label="Banner">
          <el-upload
            class="avatar-uploader avatar-uploader-left"
            :action="uploadPath"
            :show-file-list="false"
            :on-success="handleAvatarSuccess2"
            :before-upload="beforeAvatarUpload">
            <img v-if="form.infoPath" :src="form.infoPath" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <div class="upload-bottom-ts">只能上传jpg/png文件，且不超过1M，宽高比（2:1）</div>
        </el-form-item>
        <el-form-item label="活动介绍" class="fwenben">
          <editor  :menpiaoeditor="form.content" @do-editor="geteditor"/>
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
import editor from '@/components/editor'
import {upload_path} from '@/api/api'
import {getList,dataSub,dataDetail,dataDel,getType} from '@/api/huodongList'

export default {
  components: {editor},
  data() {
    return {
      time:null,
      time2:null,
      dialogFormVisible: false,
      form:{
        path:null,
        infoPath:null
      },
      uploadPath:upload_path,
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
    geteditor(data){
      this.form.content = data
    },
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
      if(this.time.length > 0){
        this.form.startTime = this.time[0]
        this.form.endTime = this.time[1]
      }
      if(this.time2.length > 0){
        this.form.enrollStartTime = this.time2[0]
        this.form.enrollEndTime = this.time2[1]
      }
      if(this.form.content.length<=0){
       this.$message.error("请填写活动介绍");
       return;
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
      const isLt2M = file.size / 1024 / 1024 < 1;
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 1MB!');
      }
      return isLt2M;
    },
    //图片上传成功
    handleAvatarSuccess(res, file,fileList) {
      if(res.status === 10000){
         this.form.path=res.data
      }else if(res.status === 20000){
        this.$message.error(res.message);
        fileList.pop()
      }
    },
    //图片上传成功
    handleAvatarSuccess2(res, file,fileList) {
      if(res.status === 10000){
         this.form.infoPath=res.data
      }else if(res.status === 20000){
        this.$message.error(res.message);
        fileList.pop()
      }
    },
    
  }
}
</script>
