<template>
  <div class="userRefresh-wrap">
    <el-breadcrumb separator-class="el-icon-arrow-right" style="font-size: 15px;line-height: 80px;width: 70%;float:left">
      <el-breadcrumb-item :to="{ path: '/admin/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{path: '/admin/Adminrefresh'}">重置信用</el-breadcrumb-item>
      <el-breadcrumb-item>用户信用分</el-breadcrumb-item>
      <el-breadcrumb-item>{{this.$route.query.username}}</el-breadcrumb-item>
    </el-breadcrumb>
    <div  style="display: flex;justify-content: center;align-items: center;width:10%;float: right;line-height: 80px">
      <el-button type="text" style="margin-left: 15px;color: beige;background-color: transparent;height: 80px">信用分</el-button>
    </div>
    <div style="display: flex;flex-direction: column;background-color: white;width: 50%;margin-left: 20%;min-height: 100px;padding: 50px;text-align: center;border-radius: 10px">
      <p>
        <label>用户名： {{credit.username}}</label>
      </p>
      <p>
        <label>信用分： {{credit.credit}}</label>
      </p>
      <p>
        <el-button @click="change">重置信用分</el-button>
      </p>
    </div>

  </div>
</template>

<script>
import store from '../../../../store'
export default {
  name: 'userrefresh',
  inject: ['reload'],
  data () {
    return {
      name: this.$route.query.username,
      credit: {}
    }
  },
  mounted () {
    this.getCreditRecords()
  },
  methods: {
    getCreditRecords () {
      this.$axios.get('/Userrefresh?username=' + this.$route.query.username).then(resp => {
        if (resp && resp.status === 200) {
          this.credit = resp.data
        }
      })
    },
    change () {
      this.$axios.post('/CreditRefresh', {
        'adminName': store.state.username,
        'username': this.$route.query.username
      }).then(resp => {
        if (resp && resp.status === 200) {
          this.$message.success('重置成功')
          this.reload()
        }
      }).catch(error => {
        this.$message.warning(error)
      })
    }
  }
}
</script>

<style scoped>
  .userRefresh-wrap {
    width: 80%;
    margin-left: 10%;
  }
</style>
