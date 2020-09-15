<template>
  <div class="app-container">
    <el-form :inline="true" :model="listQuery" class="searchForm">
      <el-form-item label="名称">
        <el-input
          v-model="listQuery.nickname"
          class="seach"
          placeholder="角色名称"
          maxlength="10"
          size="small"
          clearable/>
      </el-form-item>
      <el-form-item label="状态">
        <el-select class="seach" v-model="listQuery.status" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
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
      <div class="seachBtn">
        <el-button icon="el-icon-search" @click="fetchData" circle/>
        <el-button icon="el-icon-refresh" @click="restSearch" circle/>
      </div>
    </el-form>
    <div style="margin-bottom:10px;">
      <el-button type="success" size="small" class="form-btn-diy" @click="dialogFormVisible=true">新增用户</el-button>
      <el-button type="primary" size="small" class="form-btn-diy" @click="batchEnable">批量启用</el-button>
      <el-button type="danger" size="small" class="form-btn-diy" @click="batchDisable">批量禁用</el-button>
      <el-button type="warning" size="small" class="form-btn-diy" @click="batchReset">批量重置密码</el-button>
    </div>
    <el-table
      v-loading="listLoading"
      ref="multipleSelection"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      size="small"
      height="600px"
      highlight-current-row
      @select="showSelect"
      @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"
        width="55"/>
        <el-table-column align="center"  fixed="left" label="用户名称">
          <template slot-scope="scope">
            {{ scope.row.nickname }}
          </template>
        </el-table-column>
        <el-table-column label="手机号"  align="center">
          <template slot-scope="scope">
            {{ scope.row.mobile }}
          </template>
        </el-table-column>
        <el-table-column label="用户名"  align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.username }}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态"  align="center">
          <template slot-scope="scope">
            <el-tag type="error">{{ scope.row.statusStr }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="创建人"  align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.createBy }}</span>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center">
          <template slot-scope="scope">
            <i class="el-icon-time"/>
            <span>{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="460">
          <template slot-scope="scope">

              <el-button type="primary" @click="toTree(scope.row.id)">角色</el-button>

              <el-button type="info" @click="toEdit(scope.row.id)">编辑</el-button>

              <el-button type="success" @click="toEnable(scope.row.id)">启用</el-button>

              <el-button type="danger" @click="toDisable(scope.row.id)">禁用</el-button>

              <el-button type="warning" @click="toReset(scope.row.id)">重置密码</el-button>

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
    <el-dialog title="编辑用户" :visible.sync="dialogFormVisible" width="40%">
      <el-form :model="form" label-width="80px" >
        <el-form-item label="用户昵称">
          <el-input v-model="form.nickname" class="from_i_12"></el-input>
        </el-form-item>
        <el-form-item v-if="show" label="用户名">
          <el-input v-model="form.username" class="from_i_12"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="form.mobile" class="from_i_12"></el-input>
        </el-form-item>
        <el-form-item v-if="show" label="密码">
          <el-input v-model="form.password" class="from_i_12" show-password></el-input>
        </el-form-item>
        <el-form-item v-if="show" label="确认密码">
          <el-input v-model="password" class="from_i_12" show-password></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <div>
            <el-radio v-model="form.status" label="1" border>启用</el-radio>
            <el-radio v-model="form.status" label="2" border>禁用</el-radio>
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="onSubmit">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="分配权限" :visible.sync="dialogTreeVisible" width="30%">
      <el-tree
        ref="roleTree"
        :data="selection"
        show-checkbox
        node-key="id"
        :default-expanded-keys="selectionCheck"
        :default-checked-keys="selectionCheck"
        :props="defaultProps"/>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTreeVisible = false">取 消</el-button>
        <el-button type="primary" @click="onChangeRole">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
/* eslint-disable */
import { getRoles } from '@/api/role'
import { getList, findInfo, commit, enable, disable, reset, commitRole, findRoles } from '@/api/user'

  export default {
    data() {
      return {
        options: [
          {
            value: 1,
            label: '启用'
          },
          {
            value: 2,
            label: '禁用'
          }
        ],
        defaultProps: {
          label: 'name'
        },
        list: null,
        listSize: 0,
        listLoading: false,
        show: true,
        dialogFormVisible: false,
        dialogTreeVisible: false,
        selection: [],
        selectionCheck: [],
        ids: null,
        password: null,
        time: [],
        form: {
          id: null,
          nickname: null,
          username: null,
          mobile: null,
          status: null,
          password: null
        },
        treeForm: {
          ids: null,
          id: null
        },
        tableData: '',
        formLabelWidth: '120px',
        listQuery:{
          offset:1,
          limmit:10,
          name: null,
          startTime: null,
          endTime: null,
          status: null
        }
      }
    },
    watch: {
      dialogFormVisible(newVal){
          if(!newVal){
              this.form = {
                id: null,
                nickname: null,
                username: null,
                mobile: null,
                status: null,
                password: null
              }
              this.show = true
          }
      },
      dialogTreeVisible(newVal){
        if(!newVal){
          this.treeForm = {
            ids: null,
            id: null
          }
          this.selectionCheck = []
        }
      }
    },
    created:function(){
      this.fetchData()
    },
    methods: {
      fetchData() {
        this.listLoading = true
        if(this.time.length > 0){
            this.listQuery.startTime = this.time[0]
            this.listQuery.endTime = this.time[1]
        }
        getList(this.listQuery).then(response => {
          this.list = response.data.list
          this.listLoading = false
          this.listSize = response.data.total
        })
      },
      currentChange() {
        this.listQuery.offset = this.$refs.currentPage.internalCurrentPage
        this.listLoading = true
        getList(this.listQuery).then(response => {
          this.list = response.data.list
          this.listLoading = false
          this.listSize = response.data.total
        })
      },
      restSearch() {
        this.listQuery = {
          offset:1,
          limmit:10,
          name: null,
          startTime: null,
          endTime: null,
          status: null
        }
        this.time = []
        this.fetchData()
      },
      showSelect(selection) {
        const rows = []
        selection.forEach(row => {
          rows.push(row.id)
        })
        this.rows = rows
      },
      open2() {
        this.$message({
          message: '操作成功',
          type: 'success'
        });
      },
      toTree(id) {
        getRoles().then(res => {
            this.selection = res.data;
        })
        findRoles(id).then(res => {
            this.selectionCheck = res.data
        })
        this.treeForm.id = id
        this.dialogTreeVisible = true
      },
      toEdit(id) {
        this.show = false
        findInfo(id).then(res => {
          this.form = {
            nickname: res.data.nickname,
            username: null,
            mobile: res.data.mobile,
            password: null,
            id: res.data.id,
            status: res.data.status+''
          }
          this.dialogFormVisible=true
        })
      },
      batchEnable() {
        this.$confirm('是否批量启用角色', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          enable(this.ids).then(res => {
            this.open2()
            this.currentChange()
          })
        })
      },
      toEnable(id) {
        this.$confirm('是否启用该角色', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          enable(id).then(res => {
            this.open2()
            this.currentChange()
          })
        })
      },
      batchDisable() {
        this.$confirm('是否批量禁用角色', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          disable(this.ids).then(res => {
            this.open2()
            this.currentChange()
          })
        })
      },
      toDisable(id) {
        this.$confirm('是否禁用该角色', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          disable(id).then(res => {
            this.open2()
            this.currentChange()
          })
        })
      },
      batchReset() {
        this.$confirm('是否批量重置选中用户密码为 [ 12345678 ]', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          reset(this.ids).then(res => {
            this.open2()
            this.currentChange()
          })
        })
      },
      toReset(id) {
        this.$confirm('是否重置密码为 [ 12345678 ]', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          reset(id).then(res => {
            this.open2()
            this.currentChange()
          })
        })
      },
      handleSelectionChange(val) {
        var array = [];
        val.forEach(val => {
          array.push(val.id)
        })
        this.ids = array.join(",")
      },
      onChangeRole() {
        var ids = this.$refs.roleTree.getCheckedKeys();
        this.treeForm.ids = ids.join(',')
        commitRole(this.treeForm).then(res => {
          this.dialogTreeVisible=false
          this.open2()
          this.currentChange()
        })
      },
      onSubmit(){
        commit(this.form).then(res => {
           this.dialogFormVisible=false
           this.open2()
           this.currentChange()
        })
      }
    }
  }
</script>


<style lang="scss" scoped>
  .from_i_12 {
      width: 85%;
  }
</style>
