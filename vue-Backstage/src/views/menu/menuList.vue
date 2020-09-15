<template>
  <div class="app-container">
    <el-form :inline="true" :model="listQuery" class="searchForm">
      <el-form-item label="菜单名称">
        <el-input
          v-model="listQuery.name"
          class="seach"
          placeholder="菜单名称"
          maxlength="10"
          size="small"
          clearable/>
      </el-form-item>
      <div class="seachBtn">
        <el-button icon="el-icon-search" @click="fetchData" circle/>
      </div>
    </el-form>
    <div>
      <el-button type="success" size="small" class="form-btn-diy" @click="dialogFormVisible=true">新增菜单</el-button>
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
        <el-table-column align="center" label="菜单名称">
          <template slot-scope="scope">
            {{ scope.row.name }}
          </template>
        </el-table-column>
        <el-table-column label="菜单url" align="center">
          <template slot-scope="scope">
            {{ scope.row.url }}
          </template>
        </el-table-column>
        <el-table-column label="类型"  align="center">
          <template slot-scope="scope">
            <el-tag type="error">{{ scope.row.typeStr }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="授权"  align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.perms }}</span>
          </template>
        </el-table-column>
        <el-table-column label="排序"  align="center">
          <template slot-scope="scope">
            <el-tag type="warning">{{ scope.row.orderNum }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="180">
          <template slot-scope="scope">

              <el-button type="primary" @click="toEdit(scope.row.id)">编辑</el-button>

              <el-button type="danger" @click="toDelete(scope.row.id)">删除</el-button>

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
    <el-dialog title="编辑菜单" :visible.sync="dialogFormVisible" width="40%">
      <el-form :model="form" label-width="80px" >
        <el-form-item label="菜单名称">
          <el-input v-model="form.name" class="from_i_12"></el-input>
        </el-form-item>
        <el-form-item label="菜单url">
          <el-input v-model="form.url" class="from_i_12"></el-input>
        </el-form-item>
        <el-form-item label="授权">
          <el-input v-model="form.perms" class="from_i_12"></el-input>
        </el-form-item>
        <el-form-item label="父级菜单">
          <el-select v-model="form.parentId" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.name"
              :value="item.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="form.type" placeholder="请选择活动区域">
            <el-option label="菜单" value="1"></el-option>
            <el-option label="功能" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.orderNum" :min="1" :max="10" label="描述文字"></el-input-number>
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
import { getList, addMenu, editMenu, getMenus, findInfo, removeMenu } from '@/api/menu'

  export default {
    data() {
      return {
        options: [],
        list: null,
        listSize: 0,
        listLoading: false,
        dialogFormVisible: false,
        form: {
          id: null,
          name: null,
          url: null,
          perms: null,
          type: null,
          parentId: null,
          orderNum: null,
        },
        tableData: '',
        formLabelWidth: '120px',
        listQuery:{
          offset:1,
          limmit:10,
          name: null
        }
      }
    },
    watch: {
      dialogFormVisible(newVal){
          if(!newVal){
              this.form = {
                id: null,
                name: null,
                url: null,
                perms: null,
                type: null,
                parentId: null,
                orderNum: null,
              }
          }else {
              this.toTree()
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
      toTree() {
        getMenus().then(res => {
            this.options = res.data;
        })
      },
      toEdit(id) {
        findInfo(id).then(res => {
            this.form = {
              id: id,
              name: res.data.name,
              url: res.data.perms,
              perms: res.data.perms,
              type: res.data.type + '',
              parentId: res.data.parentId,
              orderNum: res.data.orderNum,
            }
            this.dialogFormVisible = true
        })
      },
      toDelete(id) {
        this.$confirm('此操作将永久删除该菜单, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          removeMenu(id).then(res => {
            this.open2()
            this.currentChange()
          })
        })
      },
      onSubmit(){
        if(this.form.id == null){
          addMenu(this.form).then(res => {
            this.dialogFormVisible=false
            this.open2()
            this.currentChange()
          })
        }else {
          editMenu(this.form).then(res => {
            this.dialogFormVisible=false
            this.open2()
            this.currentChange()
          })
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

