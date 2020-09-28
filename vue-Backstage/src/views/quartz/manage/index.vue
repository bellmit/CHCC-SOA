<template>
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="全部任务" name="first">
          <div style="margin-bottom:10px;">
            <el-button type="success" size="small" class="form-btn-diy" @click="dialogFormVisible=true; form.type = 1;disabled=false">新增任务</el-button>
          </div>
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
              <el-table-column label="定时任务实现类" width="300" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.jobClassName }}</span>
                </template>
              </el-table-column>
              <el-table-column label="时间表达式" width="300" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.jobTime }}</span>
                </template>
              </el-table-column>
               <el-table-column label="定时器类型"  width="220" align="center">
                <template slot-scope="scope">
                  <el-tag type="error">{{ scope.row.schedName }}</el-tag>
                </template>
              </el-table-column>
              <el-table-column label="任务说明" width="500"  align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.description }}</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="220" fixed="right">
                <template slot-scope="scope">
                  <el-button type="success" size="small" @click="toRun(scope.row)">执行</el-button>
                  <el-button type="info" size="small" @click="toEdit(scope.row)">编辑</el-button>
                  <el-button type="danger" size="small" @click="toDelete(scope.row)">删除</el-button>
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
        </el-tab-pane>

      <el-tab-pane label="进行中的任务" name="last">
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
            <el-table-column align="center" width="220" label="定时任务名称">
              <template slot-scope="scope">
                {{ scope.row.jobName }}
              </template>
            </el-table-column>
            <el-table-column label="任务分组" width="220"  align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.jobGroupName }}</span>
              </template>
            </el-table-column>
            <el-table-column label="任务说明" width="500"  align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.description }}</span>
              </template>
            </el-table-column>
            <el-table-column label="时间表达式" width="300" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.jobTime }}</span>
              </template>
            </el-table-column>
            <el-table-column label="任务状态"  width="220" align="center">
              <template slot-scope="scope">
                <el-tag type="error">{{ scope.row.jobStatus }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="160" align="center">
              <template slot-scope="scope">
                <el-button type="success" size="small" @click="resumeJob(scope.row)">恢复</el-button>
                <el-button type="danger" size="small" @click="pauseJob(scope.row)">暂停</el-button>
              </template>
            </el-table-column>
          </el-table>
      </el-tab-pane>
    </el-tabs>

    <el-dialog title="编辑定时器任务" :visible.sync="dialogFormVisible" width="40%">
      <el-form :model="form" label-width="110px" >
        <el-form-item label="定时任务名称:">
          <el-input v-model="form.jobName" class="from_i_12" :disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="任务分组:">
          <el-input v-model="form.jobGroup" class="from_i_12" :disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="实现类:">
          <el-input v-model="form.jobClassName" class="from_i_12" :disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="任务说明:">
          <el-input v-model="form.description" class="from_i_12" :disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="时间表达式:">
          <el-input v-model="form.jobTime" placeholder="时间表达式格式为 * * * * * ?" class="from_i_12"></el-input>
        </el-form-item>
        <el-form-item label="所需数据:" v-if="!disabled">
          <el-input
            class="from_i_12"
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="数据类型为json,无数据时可为空"
            v-model="map">
          </el-input>
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
/* eslint-disable */
import { list, getList, deleteJob, pause, resume, run, add, edit } from '@/api/quartz'

  export default {
    data() {
      return {
        activeName: 'first',
        departmentId: null,
        list: null,
        listSize: 0,
        form: {},
        map: null,
        listQuery: {
          offset:1,
          limmit:10,
        },
        disabled: false,
        listLoading: false,
        dialogFormVisible: false
      }
    },
    watch: {
      dialogFormVisible(newVal) {
        if(!newVal){
          this.form = {}
          this.map = null
        }
      }
    },
    created:function(){
      this.fetchData()
    },
    methods: {
      fetchData() {
        this.listLoading = true
        getList(this.listQuery).then(response => {
          this.list = response.data.records
          this.listLoading = false
          this.listSize = response.data.total
        })
      },
      runData() {
        this.listLoading = true
        list().then(response => {
          this.list = response.data
          this.listLoading = false
        })
      },
      currentChange() {
        this.listQuery.offset = this.$refs.currentPage.internalCurrentPage
        this.listLoading = true
        getList(this.listQuery).then(response => {
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
      },
      open2(res) {
        this.$notify({
          title: res.code == 10000?'成功':'错误',
          message: res.message,
          type: res.code == 10000?'success':'error'
        });
      },
      addDepartment(id) {
        this.form.parentId = id;
        this.form.type = 0;
        this.dialogFormVisible=true
      },
      toEdit(data) {
        this.form = {
              jobClassName: data.jobClassName,
              jobName: data.jobName,
              jobGroup: data.jobGroup,
              description: data.description,
              jobTime: data.jobTime
            }
        this.dialogFormVisible=true
        this.disabled=true
      },
      toDelete(data) {
        this.form = {
          jobName: data.jobName,
          jobGroup: data.jobGroup,
          description: data.description,
          jobTime: data.jobTime
        }
        this.$confirm('是否删除', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteJob(this.form).then(res => {
            this.open2(res)
            this.currentChange()
          })
        })
      },
      pauseJob(data) {
        this.form = {
          jobName: data.jobName,
          jobGroup: data.jobGroup,
          description: data.description,
          jobTime: data.jobTime
        }
        pause(this.form).then(res => {
          this.open2(res)
          this.currentChange()
        })
      },
      resumeJob(data) {
        this.form = {
          jobName: data.jobName,
          jobGroup: data.jobGroup,
          description: data.description,
          jobTime: data.jobTime
        }
        resume(this.form).then(res => {
          this.open2(res)
          this.currentChange()
        })
      },
      toRun(data) {
        this.form = {
          jobName: data.jobName,
          jobGroup: data.jobGroup,
          description: data.description,
          jobTime: data.jobTime
        }
        run(this.form).then(res => {
          this.open2(res)
          this.currentChange()
        })
      },
      onSubmit(){
        if(this.disabled){
          edit(this.form).then(res => {
            this.dialogFormVisible=false
            this.open2(res)
            this.currentChange()
          })
        }else{
          this.form.jobDataMap = JSON.parse(this.map);
          add(this.form).then(res => {
            this.dialogFormVisible=false
            this.open2(res)
            this.currentChange()
          })
        }
      },
      handleClick(tab, event) {
        if(this.activeName == 'first'){
          this.currentChange();
        }else{
          this.runData();
        }
      }
    }
  }
</script>


<style lang="scss" scoped>
  .from_i_12 {
      width: 85%;
  }
</style>

