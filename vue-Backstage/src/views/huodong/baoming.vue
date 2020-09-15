<template>
  <div class="app-container">
    <el-form :inline="true" :model="select" class="searchForm">
      <!-- <el-form-item label>
        <el-select v-model="select.productName" placeholder="请选择活动名称">
          <el-option
            :key="item.label"
            v-for="item in typeList"
            :label="item.label"
            :value="item.code"
          ></el-option>
        </el-select>
      </el-form-item> -->
      <el-form-item label="请输入活动名称">
        <el-input v-model="select.productName" class="seach" placeholder="请输入活动名称" size="small" clearable />
      </el-form-item>
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
      <el-form-item label="请输入身份证号">
        <el-input
          v-model="select.idCard"
          class="seach"
          placeholder="请输入身份证号"
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

      <el-table-column label="活动名称" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.productName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="报名人数" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.count }}</span>
        </template>
      </el-table-column>
      <el-table-column label="姓名" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.memberName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="手机号" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.mobile }}</span>
        </template>
      </el-table-column>
      <el-table-column label="身份证号" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.idCard }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否参与" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status==1" type="success" effect="dark">参与</el-tag>
          <el-tag v-if="scope.row.status!=1" type="warning" effect="dark">待参与</el-tag>
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
  </div>
</template>

<script>
import { upload_path } from "@/api/api";
import { getList, dataSub, dataDetail, dataDel, getType } from "@/api/baoming";

export default {
  data() {
    return {
      dialogFormVisible: false,
      form: {
        id: null,
        sendUrl: null,
        path: null
      },
      uploadPath: upload_path,
      list: null,
      listLoading: true,
      listSize: 0,
      typeList: null, //活动列表
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
    this.getType(); //活动名称列表
  },
  methods: {
    getType() {
      getType().then(res => {
        if (res.status === 10000) {
          this.typeList = res.data;
        } else if (res.status === 20000) {
          this.$message.error(res.message);
        }
      });
    },
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
