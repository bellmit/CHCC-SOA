<template>
  <div class="app-container">
    <el-form :inline="true" :model="listQuery" class="searchForm">
      <el-form-item label="字典名称">
        <el-input
          v-model="listQuery.name"
          class="seach"
          placeholder="字典名称"
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
      <el-button type="success" size="small" class="form-btn-diy" @click="dialogFormVisible=true">新增字典</el-button>
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
        <el-table-column align="center" label="字典名称">
          <template slot-scope="scope">
            {{ scope.row.name }}
          </template>
        </el-table-column>
        <el-table-column align="center" label="字典key">
          <template slot-scope="scope">
            {{ scope.row.dictKey }}
          </template>
        </el-table-column>
        <el-table-column align="center" label="字典value">
          <template slot-scope="scope">
            {{ scope.row.value }}
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
        <el-table-column align="center" label="字典备注">
          <template slot-scope="scope">
            {{ scope.row.content }}
          </template>
        </el-table-column>
        <el-table-column label="创建人"  align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.createBy }}</span>
          </template>
        </el-table-column>
        <el-table-column label="更新时间" align="center">
          <template slot-scope="scope">
            <i class="el-icon-time"/>
            <span>{{ scope.row.createTime == null?scope.row.updateTime:scope.row.createTime  }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template slot-scope="scope">
              <el-button type="info" size="small" @click="toEdit(scope.row.id)" v-if="scope.row.pid == null">编辑</el-button>
              <el-button type="danger" size="small" @click="toDelete(scope.row.id)" v-if="scope.row.pid == null">删除</el-button>
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
    <el-dialog title="编辑字典" :visible.sync="dialogFormVisible" width="40%">
      <el-form :model="form" label-width="80px" >
        <el-form-item label="名称：">
          <el-input v-model="form.name" class="from_i_12"></el-input>
        </el-form-item>
        <el-form-item label="key：">
          <el-input v-model="form.dictKey" class="from_i_12"></el-input>
        </el-form-item>
        <el-form-item label="value：">
          <el-input v-model="form.value" class="from_i_12"></el-input>
        </el-form-item>
        <el-form-item label="备注：">
          <el-input type="textarea" v-model="form.content" class="from_i_12"></el-input>
        </el-form-item>
        <el-form-item
          v-for="(dict, index) in form.children"
          :label="'子字典' + index + ':'"
          :key="dict.key"
          :prop="'children.' + index + '.value'"
          :rules="{
            required: true, message: '内容无法为空', trigger: 'blur'
          }"
        >
          <span>名称: <el-input style="width:133px" v-model="dict.name"/></span>
          <span>key: <el-input style="width:133px" v-model="dict.dictKey"/></span>    
          <span>value: <el-input style="width:133px" v-model="dict.value"/></span>
          <el-button @click.prevent="removeDomain(dict)">删除</el-button>
        </el-form-item>
        <el-form-item label="状态：">
          <div>
            <el-radio v-model="form.status" label="1" border>启用</el-radio>
            <el-radio v-model="form.status" label="2" border>禁用</el-radio>
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addDomain">新增子字典</el-button>
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="onSubmit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
/* eslint-disable */
import { list, findId, commit, changeStatus, deletedict } from '@/api/dict'

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
        addOrEdit: true,
        form: {
          id: null,
          pid: null,
          name: null,
          dictKey: null,
          value: null,
          status: null,
          children: [],
          content: null
        },
        treeForm: [],
        formLabelWidth: '120px',
        listQuery:{
          offset:1,
          limmit:10,
          name: null,
          status: null
        }
      }
    },
    watch: {
      dialogFormVisible(newVal){
          if(!newVal){
              this.form = {
                id: null,
                pid: null,
                name: null,
                dictKey: null,
                value: null,
                status: null,
                children: [],
                content: null
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
      toEdit(id) {
        findId(id).then(res => {
          this.form = {
            id: res.data.id,
            pid: res.data.pid,
            name: res.data.name,
            dictKey: res.data.dictKey,
            value: res.data.value,
            status: res.data.status.toString(),
            children: res.data.children,
            content: res.data.content
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
          deletedict({id: id}).then(res => {
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
      onSubmit(){
        console.log(this.form)
        commit(this.form).then(res => {
           this.dialogFormVisible=false
           this.open2(res)
           this.currentChange()
        })
      },
      removeDomain(item) {
        var index = this.form.children.indexOf(item)
        if (index !== -1) {
          this.form.children.splice(index, 1)
        }
      },
      addDomain() {
        this.form.children.push({
          value: '',
          key: Date.now()
        });
      }
    }
  }
</script>


<style lang="scss" scoped>
  .from_i_12 {
      width: 85%;
  }
</style>

