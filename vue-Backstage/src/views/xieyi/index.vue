<template>
  <div class="dashboard-container">
      <editor  :menpiaoeditor="menpiaoeditor" @do-editor="geteditor"/>
      <div class="fu_box">
        <el-button type="primary" @click="onSubmit">确 定</el-button>
      </div>
  </div>
</template>

<script>
import editor from '@/components/editor'
import {getData,subData} from '@/api/fu'
export default {
    components: {editor},
    data() {
      return {
        menpiaoeditor:'',
      }
    },
  created() {
      this.getData()
  },
  methods: {
    geteditor(data){
      this.menpiaoeditor = data
    },
    onSubmit() {
      var data = {
        info:''
      }
      data.info = this.menpiaoeditor
      subData(data).then(res => {
          if(res.status === 10000){
            this.$message.success('修改成功');
            this.getData()
            }else if(res.status === 20000){
            this.$message.error(res.message);
          }
      })
    },
    getData() {
      getData().then(res => {
          if(res.status === 10000){
            this.menpiaoeditor = res.data.info
            }else if(res.status === 20000){
            this.$message.error(res.message);
          }
      })
    },

    
  }
}
</script>
<style scoped>
.dashboard-container{
  background: #fff;
  box-sizing: border-box;
  padding: 30px;
}
.fu_box{
  margin-top: 40px;
}
</style>
