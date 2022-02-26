<template>
  <div class="article">
    <h1>{{detail.title}}</h1>
    <el-row class="indent">
      <img :src="detail.cover" alt="封面" id="pic">
      <div id="info">
        <span>作者：{{detail.author}}</span>
        <br><br>
        <span>ISBN：{{detail.isbn}}</span>
        <br><br>
        <span>出版日期：{{detail.date}}</span>
        <br><br>
        <span>馆藏地址：{{detail.area.name}}</span>
        <br><br>
        <span>副本数量：{{detail.number}}</span>
        <br><br>
        <span>价格：{{detail.price}}</span>
      </div>
      <div style="margin-left:150px;margin-top: 50px;max-height:60px;padding:15px;border-radius:10px;border: 1px solid #dedfe6">
        <label style="font-size: 15px;">评分：</label><br><br>
        <el-rate
          v-model="detail.rate"
          disabled
          style="display: inline-block"
        >
        </el-rate>
        <span style="color: #ff9900;display: inline-block">{{detail.rate}}</span>
      </div>
    </el-row>
    <el-row class="intro">
      <h3>内容简介：</h3>
      {{detail.intro}}
    </el-row>
    <el-row class="comment">
      <h3>书评：</h3>
      <el-divider></el-divider>
      <span style="color:#909399;font-size: 14px" v-if="this.commentList.length === 0">暂无书评</span>
      <div class="comment-box" style="text-align: left" v-else>
        <div class="comment" v-for="item in commentList.slice((currentPage-1)*pagesize, currentPage*pagesize)" :key="item.commentId">
          <div class="info" style="display: flex">
            <img
              class="avatar"
              :src="circleUrl"
              width="36"
              height="36"
            />
            <div class="right">
              <div class="right-top">
                <div class="name">{{ item.userName }}</div>
                <div class="rate">
                  <el-rate
                    v-model="item.rate"
                    disabled
                  >
                  </el-rate>
                </div>
              </div>
              <div class="date">{{ item.time }}</div>
            </div>
          </div>
          <el-button disabled v-if="item.content === 'This comment has been deleted.'" style="background-color: #f4f8f4;color: black;border-radius: 10px;font-family: 华光细圆_CNKI;width: 100px;float: right">已删除</el-button>
          <el-button @click="deleteComment(item.commentId)" v-else style="background-color: #f4f8f4;color: black;border-radius: 10px;font-family: 华光细圆_CNKI;width: 100px;float: right">删除评论</el-button>
          <div class="content">{{ item.content }}</div>
          <div class="reply">
            <div class="item" v-for="reply in item.replyList" :key="reply.id">
              <div class="reply-content">
                <span class="from-name">{{reply.userName}}</span>
                <span>回复</span>
                <span class="to-name">{{reply.replyUsername}}</span>
                <span>: </span>
                <span>{{ reply.content }}</span>
              </div>
              <div class="reply-bottom" style="margin-top: 5px">
                <span style="font-size: 12px;color:#909399">{{ reply.time }}</span>
                <span>
                  <el-button type="text" disabled v-if="reply.content === 'This reply has been deleted!'" style="color: black;margin-left: 10px;background:transparent;border: none">已删除</el-button>
                  <el-button type="text" @click="deleteReply(reply.id)" v-else style="color: #409eff;margin-left: 10px;background: transparent">删除</el-button>
                </span>
              </div>
            </div>
          </div>
          <el-divider></el-divider>
        </div>
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="pagesize"
          :total="commentList.length">
        </el-pagination>
      </div>
    </el-row>
  </div>
</template>

<script>
import store from '../../../store'
export default {
  name: 'DetailShowLib',
  inject: ['reload'],
  data () {
    return {
      detail: {},
      circleUrl: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
      commentList: [],
      currentPage: 1,
      pagesize: 5
    }
  },
  mounted () {
    this.getDetails()
  },
  methods: {
    getDetails () {
      var id = this.$route.query.id
      const that = this
      this.$axios.get('/getDetails/' + id).then(resp => {
        if (resp) {
          that.detail = resp.data
          that.getCommentList(resp.data.isbn)
        }
      })
    },
    getCommentList (isbn) {
      var _this = this
      this.$axios.get('/getCommentList/' + isbn).then(resp => {
        if (resp && resp.status === 200) {
          _this.commentList = resp.data
        }
      }).catch(error => {
        console.log(error)
      })
    },
    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage
    },
    deleteComment (commentId) {
      this.$axios.post('/deleteComment', {
        'commentId': commentId,
        'userName': store.state.username
      }).then(resp => {
        if (resp && resp.status === 200) {
          this.$message.success('删除评论成功！')
          this.reload()
        } else {
          this.$message.error('删除评论失败')
        }
      })
    },
    deleteReply (id) {
      this.$axios.post('/deleteReply', {
        'replyId': id
      }).then(resp => {
        if (resp && resp.status === 200) {
          this.$message.success('删除回复成功！')
          this.reload()
        } else {
          this.$message.error('删除回复失败')
        }
      })
    }
  }
}
</script>

<style scoped>
  .article {
    text-align: left;
    margin-left: 10%;
    margin-right: 10%;
    min-height: 430px;
    background-color: white;
    color: black;
    padding: 25px 50px 50px;
  }
  .indent {
    display: flex;
    font-size: 14px;
    line-height: 20px;
  }
  #pic {
    width: 150px;
    height: 190px;
    display: inline-block;
  }
  #info {
    display: inline-block;
    margin-left: 50px;
  }
  .right {
    display: flex;
    flex-direction: column;
    margin-left: 10px;
  }
  .right-top {
    display: flex;
  }
  .name {
    font-size: 15px;
  }
  .rate {
    margin-left: 20px;
  }
  .date {
    font-size: 12px;
    margin-top: 5px;
    color:#909399;
  }
  .content {
    padding: 10px 0;
  }
  .reply {
    border-left: 2px solid #dcdfe6;
    margin: 10px 0;
    background-color: #f5f7fa;
  }
  .item {
    margin: 0 10px;
    padding: 10px 0;
    border-bottom: 1px dashed #dedfe6;
  }
  .reply-content {
    font-size: 14px;
  }
  .from-name {
    color: #409eff;
    margin-right: 5px;
  }
  .to-name {
    color: #409eff;
    margin-left: 5px;
  }
  .reply-text {
    font-size: 14px;
    margin-left: 10px;
    color:#909399
  }
</style>
