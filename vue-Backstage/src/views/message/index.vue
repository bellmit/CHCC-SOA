<template>
  <div class="app-container">
    <el-form :inline="true" :model="select" class="searchForm">
      <el-form-item label="请输入姓名">
        <el-input v-model="select.name" class="seach" placeholder="请输入姓名" size="small" clearable />
      </el-form-item>
      <el-form-item label="请输入手机号">
        <el-input
          v-model="select.mobile"
          class="seach"
          placeholder="请输入手机号"
          maxlength="11"
          size="small"
          clearable
        />
      </el-form-item>
      <div class="seachBtn">
        <el-button icon="el-icon-search" @click="currentChange(1)" circle />
        <el-button icon="el-icon-refresh" @click="restSearch" circle />
      </div>
    </el-form>
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
      <el-table-column label="编号" align="center">
        <template slot-scope="scope">{{ scope.row.id }}</template>
      </el-table-column>
      <el-table-column label="姓名" align="center">
        <template slot-scope="scope">{{ scope.row.name }}</template>
      </el-table-column>
      <el-table-column label="手机号" align="center">
        <template slot-scope="scope">{{ scope.row.mobile }}</template>
      </el-table-column>
      <el-table-column label="内容" align="center">
        <template slot-scope="scope">{{ scope.row.content }}</template>
      </el-table-column>
      <el-table-column label="时间" align="center">
        <template slot-scope="scope">{{ scope.row.createTime }}</template>
      </el-table-column>
      <el-table-column label="回复内容" align="center">
        <template slot-scope="scope">{{ scope.row.reply }}</template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button type="primary" @click.native.prevent="toEdit(scope.row.id)">回复</el-button>
          <el-button type="danger" @click.native.prevent="doDel(scope.row.id)">删除</el-button>
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
      ></el-pagination>
    </div>
    <el-dialog
      title="留言管理"
      @close="guanbi"
      :visible.sync="dialogFormVisible"
      width="40%"
      :close-on-click-modal="false"
    >
      <el-form :model="form" label-width="80px">
        <el-form-item label="回复内容">
          <el-input v-model="form.reply" class="from_i_12"></el-input>
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
import { upload_path } from "@/api/api";
import { getList, dataSub, dataDetail, dataDel } from "@/api/message";

export default {
  data() {
    return {
      dialogFormVisible: false,
      form: {
        id: null,
        reply: null
      },
      uploadPath: upload_path,
      list: null,
      listLoading: true,
      listSize: 0,
      select: {
        offset: 1,
        limmit: 10,
        name: null,
        mobile: null,
        idCard: null
      }
    };
  },
  created() {
    this.currentChange(1);
  },
  methods: {
    restSearch() {
      this.select = {
        offset: 1,
        limmit: 10,
        name: null,
        mobile: null,
        idCard: null
      };
      this.currentChange(1);
    },
    guanbi() {
      this.form = {
        id: null,
        reply: null
      };
    },
    onSubmit() {
      dataSub(this.form).then(res => {
        if (res.status === 10000) {
          this.$message.success("添加成功");
          this.dialogFormVisible = false;
          //刷新数据
          this.currentChange(1);
        } else if (res.status === 20000) {
          this.$message.error(res.message);
        }
      });
    },
    doDel(id) {
      //是否删除
      this.$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          dataDel(id).then(res => {
            if (res.status === 10000) {
              //刷新数据
              this.currentChange(1);
              this.$message({
                type: "success",
                message: "删除成功!"
              });
            } else if (res.status === 20000) {
              this.$message.error(res.message);
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    toEdit(id) {
      dataDetail(id).then(res => {
        if (res.status === 10000) {
          this.form = res.data;
          this.dialogFormVisible = true;
        } else if (res.status === 20000) {
          this.$message.error(res.message);
        }
      });
    },
    currentChange(e) {
      this.listLoading = true;
      this.select.offset = e;
      getList(this.select).then(res => {
        if (res.status === 10000) {
          this.listLoading = false;
          this.list = res.data.list;
          this.listSize = res.data.total;
        } else if (res.status === 20000) {
          this.$message.error(res.message);
        }
      });
    },
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 5;
      if (!isLt2M) {
        this.$message.error("上传图片大小不能超过 5MB!");
      }
      return isLt2M;
    },
    //图片上传成功
    handleAvatarSuccess(res, file, fileList) {
      if (res.status === 10000) {
        this.path(res.data);
      } else if (res.status === 20000) {
        this.$message.error(res.message);
        fileList.pop();
      }
    }
  }
};
</script>
