<template>
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="全部任务" name="first">
          <div style="margin-bottom:10px;">
            <el-button type="success" size="small" class="form-btn-diy" @click="dialogFormVisible=true; form.type = 1">新增任务</el-button>
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
              <el-table-column label="操作" width="160">
                <template slot-scope="scope">
                  <el-button type="info" size="small" @click="toEdit(scope.row)">编辑</el-button>
                  <el-button type="danger" size="small" @click="toDelete(scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
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
            <el-table-column label="操作" width="160">
              <template slot-scope="scope">
                <el-button type="success" size="small" @click="toDelete(scope.row)">恢复</el-button>
                <el-button type="danger" size="small" @click="toDelete(scope.row)">暂停</el-button>
              </template>
            </el-table-column>
          </el-table>
      </el-tab-pane>

    </el-tabs>


    <el-dialog title="编辑定时器任务" :visible.sync="dialogFormVisible" width="40%">
      <el-form :model="form" label-width="110px" >
        <el-form-item label="定时任务名称:">
          <el-input v-model="form.jobName" class="from_i_12"></el-input>
        </el-form-item>
        <el-form-item label="任务分组:">
          <el-input v-model="form.jobGroupName" class="from_i_12"></el-input>
        </el-form-item>
        <el-form-item label="任务说明:">
          <el-input v-model="form.description" class="from_i_12"></el-input>
        </el-form-item>
        <el-form-item label="时间表达式:">
          <el-input v-model="form.jobTime" class="from_i_12"></el-input>
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
import { list } from '@/api/quartz'

  export default {
    data() {
      return {
        activeName: 'first',
        departmentId: null,
        list: null,
        listSize: 0,
        form: {},
        listLoading: false,
        dialogFormVisible: false
      }
    },
    watch: {
      dialogFormVisible(newVal) {
        if(!newVal){
          this.form = {}
        }
      }
    },
    created:function(){
      this.fetchData()
    },
    methods: {
      fetchData() {
        this.listLoading = true
        list().then(response => {
          this.list = response.data
          this.listLoading = false
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
              jobName: data.jobName,
              jobGroupName: data.jobGroupName,
              description: data.description,
              jobTime: data.jobTime
            }
        this.dialogFormVisible=true
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
      onSubmit(){
        commit(this.form).then(res => {
           this.dialogFormVisible=false
           this.open2(res)
           this.currentChange()
        })
      },
      handleClick(tab, event) {
        
      }
    }
  }
</script>


<style lang="scss" scoped>
  .from_i_12 {
      width: 85%;
  }
</style>

