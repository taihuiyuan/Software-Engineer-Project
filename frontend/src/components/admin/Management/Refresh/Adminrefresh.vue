<template>
  <div class="adminRefresh-wrap">
    <el-breadcrumb separator-class="el-icon-arrow-right" style="font-size: 15px;line-height: 80px;width: 40%;float:left">
      <el-breadcrumb-item :to="{ path: '/admin/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{path: '/admin/Adminrefresh'}">重置信用</el-breadcrumb-item>
    </el-breadcrumb>
    <div  style="display: flex;justify-content: center;align-items: center;width:50%;float: right;line-height: 80px">
      <el-input placeholder="查询用户" @keyup.enter.native="handleSearch" prefix-icon="el-icon-search" v-model="input">
      </el-input>
      <el-button type="primary" icon="el-icon-search" @click="handleSearch" style="margin-left: 15px;">搜索</el-button>
    </div>
    <el-table class="adminRefresh" :data="credit.slice((currentPage-1)*pagesize, currentPage*pagesize)">
      <el-table-column prop="username" label="用户名" width="160">
      </el-table-column>
      <el-table-column prop="credit" label="信用分" width="160"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="refresh(scope.row.username)">重置信用分</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-row class="page">
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-size="pagesize"
        :total="credit.length">
      </el-pagination>
    </el-row>
  </div>
</template>

<script>
import store from '../../../../store'
export default {
  name: 'Adminrefresh',
  inject: ['reload'],
  data () {
    return {
      name: '',
      credit: [],
      input: '',
      currentPage: 1,
      pagesize: 8
    }
  },
  mounted () {
    this.getCreditRecords()
  },
  methods: {
    getCreditRecords () {
      this.$axios.get('/Adminrefresh').then(resp => {
        if (resp && resp.status === 200) {
          this.credit = resp.data
        }
      })
    },
    handleSearch () {
      if (this.input.length !== 5 && this.input.length !== 11) {
        this.$message.warning('请输入学号/工号')
      } else {
        this.$axios.get('/Userrefresh?username=' + this.input).then(resp => {
          if (resp && resp.status === 200) {
            this.$router.push({
              path: '/Userrefresh',
              query: {
                username: this.input
              }
            })
          }
        }).catch(error => {
          this.$message.warning(error)
        })
      }
    },
    refresh (username) {
      this.$axios.post('/CreditRefresh', {
        'adminName': store.state.username,
        'username': username
      }).then(resp => {
        if (resp && resp.status === 200) {
          this.$message.success('重置成功')
          this.reload()
        }
      }).catch(error => {
        this.$message.warning(error)
      })
    },
    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage
    }
  }
}
</script>

<style scoped>
  .adminRefresh-wrap {
    width: 80%;
    margin-left: 10%;
  }
  .adminRefresh{
    min-height: 500px;
    padding: 20px 50px 50px;
  }
  .page{
    padding: 10px;
    background-color: white;
  }
</style>
