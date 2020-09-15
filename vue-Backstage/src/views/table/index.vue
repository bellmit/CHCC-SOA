<template>
  <div class="app-container">
    <el-button type="primary" @click="dialogVisible = true">图片上传</el-button>
    <el-dialog
      title="图片上传"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      
      <el-upload
        class="upload-demo"
        :action="uploadPath"
        multiple
        :show-file-list="false"
        :limit="1"
        >
        <el-button size="small" type="primary">点击上传</el-button>
      </el-upload>
      
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
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
      <el-table-column align="center" label="ID" width="95">
        <template slot-scope="scope">
          {{ scope.$index }}
        </template>
      </el-table-column>
      <el-table-column label="Title" >
        <template slot-scope="scope">
          {{ scope.row.title }}
        </template>
      </el-table-column>
      <el-table-column label="Author" width="110" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.author }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Pageviews" width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.pageviews }}
        </template>
      </el-table-column>
      <el-table-column class-name="status-col" label="Status" width="110" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="Display_time" width="200">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <span>{{ scope.row.display_time }}</span>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="200"
        class-name="b_btnbox"
       >
        <template slot-scope="scope">
          <el-button
            icon="el-icon-delete"
            @click.native.prevent="deleteRow(scope.$index)"
            type="text"
            size="small">
            移除
          </el-button>
          <el-button
            icon="el-icon-view"
            @click.native.prevent="deleteRow(scope.$index)"
            type="text"
            size="small">
            查看
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <div class="b_pagebox">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="listSize">
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
      dialogVisible:false,
      uploadPath:upload_path,
      list: null,
      listLoading: true,
      listSize:0
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    fetchData() {
      this.listLoading = true
      sysUploadList().then(res => {
          if(res.status === 10000){
            this.listLoading = false
            this.tableDatas = res.data.list
            this.listSize = res.data.total
            }else if(res.status === 20000){
            this.$message.error(res.message);
          }
      })
    }
  }
}
</script>
