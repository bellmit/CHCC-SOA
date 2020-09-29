<template>
  <div class="app-container">
    <el-table
      v-loading="listLoading"
      ref="multipleSelection"
      :data="list"
      element-loading-text="Loading"
      border
      size="small"
      height="600px"
      highlight-current-row
      @select="showSelect">
        <el-table-column align="center" width="220" label="定时任务名称" fixed>
          <template slot-scope="scope">
            {{ scope.row.jobName }}
          </template>
        </el-table-column>
        <el-table-column label="任务分组" width="220"  align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.jobGroup }}</span>
          </template>
        </el-table-column>
         <el-table-column label="执行时间" width="220" align="center">
          <template slot-scope="scope">
            <i class="el-icon-time"/>
            <span>{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column label="定时任务实现类" width="300" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.className }}</span>
          </template>
        </el-table-column>
        <el-table-column label="任务数据" width="300" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.jobData }}</span>
          </template>
        </el-table-column>
          <el-table-column label="执行状态"  width="220" align="center">
          <template slot-scope="scope">
            <el-tag type="error">{{ scope.row.status==1?'成功':'失败' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="任务说明" width="500"  align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.description }}</span>
          </template>
        </el-table-column>
        <el-table-column label="报错信息" width="500"  align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.errMsg }}</span>
          </template>
        </el-table-column>
      </el-table>
      <div class="b_pagebox">
        <el-pagination
          ref="currentPage"
          :total="listSize"
          layout="prev, pager, next"
          @current-change="currentChange"/>
      </div>
  </div>
</template>

<script>
/* eslint-disable */
import { logsList } from '@/api/quartz'

  export default {
    data() {
      return {
        list: null,
        listSize: 0,
        form: {},
        listQuery: {
          offset:1,
          limmit:10,
        },
        listLoading: false,
      }
    },
    created:function(){
      this.fetchData()
    },
    methods: {
      fetchData() {
        this.listLoading = true
        logsList(this.listQuery).then(response => {
          this.list = response.data.records
          this.listLoading = false
          this.listSize = response.data.total
        })
      },
      currentChange() {
        this.listQuery.offset = this.$refs.currentPage.internalCurrentPage
        this.listLoading = true
        logsList(this.listQuery).then(response => {
          this.list = response.data.records
          this.listLoading = false
          this.listSize = response.data.total
        })
      },
      showSelect(selection) {
        const rows = []
        selection.forEach(row => {
          rows.push(row.id)
        })
        this.rows = rows
      }
    }
  }
</script>


<style lang="scss" scoped>
  .from_i_12 {
      width: 85%;
  }
</style>

