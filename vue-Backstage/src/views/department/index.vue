<template>
  <div class="app-container">
    <el-form :inline="true" :model="listQuery" class="searchForm">
      <el-form-item label="部门名称">
        <el-input
          v-model="listQuery.name"
          class="seach"
          placeholder="部门名称"
          maxlength="10"
          size="small"
          clearable/>
      </el-form-item>
      <div class="seachBtn">
        <el-button icon="el-icon-search" @click="fetchData" circle/>
        <el-button icon="el-icon-refresh" @click="restSearch" circle/>
      </div>
    </el-form>
    <div style="margin-bottom:10px;">
      <el-button type="success" size="small" class="form-btn-diy" @click="dialogFormVisible=true; form.type = 1">新增部门</el-button>
    </div>
    <el-table
      v-loading="listLoading"
      ref="multipleSelection"
      :data="list"
      row-key="id"
      element-loading-text="Loading"
      border
      fit
      size="small"
      height="600px"
      highlight-current-row
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      @select="showSelect">
        <el-table-column align="center" label="部门名称">
          <template slot-scope="scope">
            {{ scope.row.name }}
          </template>
        </el-table-column>
        <el-table-column label="状态"  align="center">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.status"
              :active-value="activeValue"
              active-text="启用"
              active-color="#13ce66"
              inactive-color="#ff4949"
              inactive-text="禁用"
              :inactive-value="inactiveValue"
              @change="toEnableOrDisable(scope.row.id,scope.row.status)"
            >
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="创建人"  align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.createBy }}</span>
          </template>
        </el-table-column>
        <el-table-column label="创建时间"  align="center">
          <template slot-scope="scope">
            <i class="el-icon-time"/>
            <span>{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column label="上次更新时间" align="center">
          <template slot-scope="scope">
            <i class="el-icon-time"/>
            <span>{{ scope.row.updateTime }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="310">
          <template slot-scope="scope">
              <el-button type="primary" size="small" @click="addDepartment(scope.row.id)">子部门</el-button>
              <el-button type="success" size="small" @click="addUser(scope.row.id)">用户</el-button>
              <el-button type="info" size="small" @click="toEdit(scope.row.id)">编辑</el-button>
              <el-button type="danger" size="small" @click="toDelete(scope.row.id)">删除</el-button>
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
    <el-dialog title="编辑部门" :visible.sync="dialogFormVisible" width="40%">
      <el-form :model="form" label-width="80px" >
        <el-form-item label="编辑部门">
          <el-input v-model="form.name" class="from_i_12"></el-input>
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
    <el-dialog title="分配用户" :visible.sync="dialogTreeVisible" :departmentId="departmentId" width="38%">
      <el-transfer
        filterable
        style="text-align: left; display: inline-block"
        filter-placeholder="请输入用户昵称"
        v-model="checkUser"
        :data="treeForm"
        :button-texts="['移除','增加']"
        :titles="['未加入部门用户','已加入部门用户']"
      >
      </el-transfer>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTreeVisible = false">取 消</el-button>
        <el-button type="primary" @click="onChange">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
/* eslint-disable */
import { list, findId, commit, findUser, commitUser, changeStatus, deleteDepartment } from '@/api/department'

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
        departmentId: null,
        checkUser: [],
        list: null,
        list1: null,
        listSize: 0,
        listLoading: false,
        dialogFormVisible: false,
        dialogTreeVisible: false,
        selection: [],
        activeValue: 1,
        inactiveValue: 2,
        ids: [],
        form: {
          id: null,
          parentId: null,
          name: null,
          type: null,
          status: null
        },
        treeForm: [],
        formLabelWidth: '120px',
        listQuery:{
          offset:1,
          limmit:10,
          name: null,
          createBy: null,
          status: null
        }
      }
    },
    watch: {
      dialogFormVisible(newVal){
          if(!newVal){
              this.form = {
                id: null,
                name: null,
                parentId: null,
                type: null,
                status: null
              }
          }
      },
      dialogTreeVisible(newVal){
        if(!newVal){
          this.treeForm = []
        }
      }
    },
    created:function(){
      this.fetchData()
    },
    methods: {
      fetchData() {
        this.listLoading = true
        list(this.listQuery).then(response => {
          this.list = response.data.records
          this.listLoading = false
          this.listSize = response.data.total
        })
      },
      currentChange() {
        this.listQuery.offset = this.$refs.currentPage.internalCurrentPage
        this.listLoading = true
        list(this.listQuery).then(response => {
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
      addUser(id) {
        this.dialogTreeVisible = true;
        this.departmentId = id;
        findUser(id).then(res => {
          this.checkUser = res.data.ids;
          this.treeForm = res.data.list;
        })
      },
      toEdit(id) {
        findId(id).then(res => {
          this.form = {
            id: res.data.id,
            name: res.data.name,
            status: res.data.status+''
          }
          this.dialogFormVisible=true
        })
      },
      toEnableOrDisable(id,status) {
        this.$confirm(status == 1?'是否启用':'是否禁用', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          changeStatus({id: id,status: status}).then(res => {
            this.open2(res)
            this.currentChange()
          })
        }).catch(() => {
          this.currentChange();
        })
      },
      toDelete(id) {
        this.$confirm('是否删除', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteDepartment({id: id}).then(res => {
            this.open2(res)
            this.currentChange()
          })
        })
      },
      restSearch() {
        this.listQuery = {
          offset:1,
            limmit:10,
            name: null,
            createBy: null,
            status: null
        }
        this.fetchData()
      },
      onChange() {
        const data = {
          id: this.departmentId,
          ids: this.checkUser
        }
        commitUser(data).then(res => {
          this.open2(res)
          this.dialogTreeVisible = false
        })
      },
      onSubmit(){
        commit(this.form).then(res => {
           this.dialogFormVisible=false
           this.open2(res)
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

