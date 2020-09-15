<template>
  <div class="app-container">
    <div class="b_topbox">
        <el-upload
          class="upload-demo"
          :action="uploadPath"
          :on-success="fetchData"
          multiple
          :show-file-list="false"
          >
          <el-button size="small" type="primary">点击上传</el-button>
        </el-upload>
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
      <el-table-column label="图片" align="center" class-name="b_imgBox" width="150">
        <template slot-scope="scope">
          <img :src="scope.row.domainPath" onerror="javascript:this.src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560835537905&di=fefc36c4df18783fc167340937ee13e4&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201607%2F27%2F20160727132507_yP8fe.jpeg'"  alt="">
        </template>
      </el-table-column>
      <el-table-column label="图片标题"  align="center">
        <template slot-scope="scope">
          {{ scope.row.title }}
        </template>
      </el-table-column>
      <el-table-column label="访问路径"  align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.domainPath }}</span>
        </template>
      </el-table-column>
      <el-table-column label="物理路径"  align="center">
        <template slot-scope="scope">
          {{ scope.row.filePath }}
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
import {sysUploadList} from '@/api/system'

export default {
  data() {
    return {
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
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      sysUploadList().then(res => {
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
        sysUploadList(this.select).then(res => {
            if(res.status === 10000){
              this.listLoading = false
              this.list = res.data.list
              this.listSize = res.data.total
            }else if(res.status === 20000){
              this.$message.error(res.message);
            }
        })
    }
  }
}
</script>
