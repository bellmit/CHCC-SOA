<template>
  <div class="app-container">
    <el-form :inline="true" :model="listQuery" class="searchForm">
      <el-form-item label="角色名称">
        <el-input
          v-model="listQuery.name"
          class="seach"
          placeholder="角色名称"
          maxlength="10"
          size="small"
          clearable/>
      </el-form-item>
      <el-form-item label="创建人名称">
        <el-input
          v-model="listQuery.createBy"
          class="seach"
          placeholder="创建人名称"
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
      <div class="seachBtn">
        <el-button icon="el-icon-search" @click="fetchData" circle/>
        <el-button icon="el-icon-refresh" @click="restSearch" circle/>
      </div>
    </el-form>
    <div style="margin-bottom:10px;">
      <el-button type="success" size="small" class="form-btn-diy" @click="dialogFormVisible=true">新增角色</el-button>
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
      @select="showSelect">
        <el-table-column align="center" label="角色名称">
          <template slot-scope="scope">
            {{ scope.row.name }}
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
        <el-table-column label="操作" width="300">
          <template slot-scope="scope">
              <el-button type="primary" size="small" @click="toTree(scope.row.id)">权限</el-button>
              <el-button type="info" size="small" @click="toEdit(scope.row.id)">编辑</el-button>
              <el-button type="success" size="small" @click="toEnable(scope.row.id)">启用</el-button>
              <el-button type="danger" size="small" @click="toDisable(scope.row.id)">禁用</el-button>
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
    <el-dialog title="编辑角色" :visible.sync="dialogFormVisible" width="40%">
      <el-form :model="form" label-width="80px" >
        <el-form-item label="角色名称">
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
    <el-dialog title="分配权限" :visible.sync="dialogTreeVisible" width="30%">
      <el-tree
        ref="roleTree"
        :data="selection"
        show-checkbox
        node-key="id"
        :check-strictly="true"
        :default-expanded-keys="ids"
        :default-checked-keys="ids"/>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTreeVisible = false">取 消</el-button>
        <el-button type="primary" @click="onChangeRole">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
/* eslint-disable */
import { getTree } from '@/api/menu'
import { getList, findInfo, findTree, commit, enable, disable, commitTree } from '@/api/role'

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
        list: null,
        listSize: 0,
        listLoading: false,
        dialogFormVisible: false,
        dialogTreeVisible: false,
        selection: [],
        ids: [],
        form: {
          id: null,
          name: null,
          status: null
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
                status: null
              }
          }
      },
      dialogTreeVisible(newVal){
        if(!newVal){
          this.treeForm = {
            ids: null,
            id: null
          }
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
      open2() {
        this.$message({
          message: '操作成功',
          type: 'success'
        });
      },
      toTree(id) {
        getTree().then(res => {
            this.selection = res.data;
        })
        findTree(id).then(res => {
            this.ids = res.data
        })
        this.treeForm.id = id
        this.dialogTreeVisible = true
      },
      toEdit(id) {
        findInfo(id).then(res => {
          this.form = {
            id: res.data.id,
            name: res.data.name,
            status: res.data.status+''
          }
          this.dialogFormVisible=true
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
      onChangeRole() {
        this.ids = this.$refs.roleTree.getCheckedKeys();
        this.treeForm.ids = this.ids.join(',')
        commitTree(this.treeForm).then(res => {
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

