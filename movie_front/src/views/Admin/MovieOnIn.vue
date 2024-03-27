<template>
  <el-card>
    <div style="display: flex; padding-bottom: 20px;">
      <div style="flex: 1; padding-left: 200px;">
        <el-input v-model="search" placeholder="输入电影名搜索" style="width: 300px;"></el-input>
      </div>
      <div style="display: flex;justify-content: flex-end; padding-right: 30px;">
        <el-button type="primary" @click="$router.push('/Admin/MovieAdd')">上映电影</el-button>
      </div>
    </div>
    <el-table :data="tableData.filter(data => !search || data.movieName.toLowerCase().includes(search.toLowerCase()))"
      :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }">
      <el-table-column type="selection" width="45"></el-table-column>
      <el-table-column label="电影封面" width="100">
        <template v-slot="scope">
          <el-image style="width: 80px; height: 116.5px" :src="scope.row.poster"></el-image>
        </template>
      </el-table-column>
      <el-table-column label="电影名称" prop="movieName"></el-table-column>
      <el-table-column label="上映时间" prop="releaseTime"></el-table-column>
      <el-table-column label="演员" prop="actors"></el-table-column>
      <el-table-column label="类型" prop="type"></el-table-column>
      <el-table-column label="评分" prop="score"></el-table-column>
      <el-table-column label="时长(分钟)" prop="duration"></el-table-column>
      <el-table-column label="地区" prop="region"></el-table-column>
      <el-table-column label="操作" width="250">

        <template v-slot="scope">
          <el-button type="primary" @click="handleEdit(scope.row)" plain>编辑</el-button>
          <el-button type="danger" @click="handleDelete(scope.row.movieId)" plain>下架</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px 0;" align="center">
      <el-pagination @current-change="handleCurrentChange" :current-page="pageNum" :page-size="pageSize"
        layout="total, prev, pager, next" :total="total">
      </el-pagination>
    </div>
    <el-dialog title="电影信息编辑" :visible.sync="editVisible">
      <el-form :model="editData" style="width: 80%;" label-width="80px" :rules="rules" ref="editref">
        <div style="display: flex;">
          <div>
            <el-form-item>
              <el-upload class="upload-demo" drag accept=".png,.jpg" :headers="{ token: user.token }"
                action="http://localhost:8081/file/upload" :on-success="handleAvatarSuccess">
                <img v-if="editData.poster" :src="editData.poster" class="poster" style="width: 200px;height: 290px;">
              </el-upload>
            </el-form-item>
          </div>
          <div>
            <el-form-item label="电影名称">
              <el-input v-model="editData.movieName"></el-input>
            </el-form-item>
            <el-form-item label="导演">
              <el-input v-model="editData.director"></el-input>
            </el-form-item>
            <el-form-item label="演员">
              <el-input v-model="editData.actors"></el-input>
            </el-form-item>
            <el-form-item label="电影类型">
              <el-input v-model="editData.type"></el-input>
            </el-form-item>
            <el-form-item label="制片地区">
              <el-select v-model="editData.region" placeholder="请选择制片地区">
                <el-option label="中国大陆" value="中国大陆"></el-option>
                <el-option label="中国香港" value="中国香港"></el-option>
                <el-option label="中国台湾" value="中国台湾"></el-option>
                <el-option label="日本" value="日本"></el-option>
                <el-option label="美国" value="美国"></el-option>
                <el-option label="韩国" value="韩国"></el-option>
                <el-option label="俄罗斯" value="俄罗斯"></el-option>
                <el-option label="英国" value="英国"></el-option>
                <el-option label="法国" value="法国"></el-option>
                <el-option label="印度" value="印度"></el-option>
                <el-option label="其他" value="其他"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="全片时长">
              <el-input-number v-model="editData.duration" :min="10" :max="550">分钟</el-input-number>
            </el-form-item>
            <el-form-item label="上映时间">
              <el-date-picker placeholder="选择日期" v-model="editData.releaseTime" style="width: 150px;"></el-date-picker>
            </el-form-item>
          </div>
        </div>
        <el-form-item style="width: 730px;">
          <el-input type="textarea" v-model="editData.introduction" autosize></el-input>
        </el-form-item>
        <div style="text-align: center;width: 700px;">
          <el-button type="primary" @click="update">提交</el-button>
        </div>
      </el-form>
    </el-dialog>
  </el-card>
</template>

<script>
export default {
  name: 'MovieOnIn',
  data() {
    return {
      pageNum: 1,     //当前页码
      pageSize: 3,   //每页显示多少条
      movieName: '', // 电影名称
      total: 0,       //总条数
      search: '', // 搜索框的值
      tableData: [], // 存储电影数据的数组，用于表格展示
      user: JSON.parse(localStorage.getItem('Moive_user') || '{}'), // 从浏览器的本地存储中获取token（用户信息）
      editData: {}, // 用于编辑电影信息的对象
      editVisible: false, // 控制编辑对话框的显示状态

    };
  },
  created() {
    this.load()
  },
  methods: {
    load(pageNum) {
      if (pageNum) {
        this.pageNum = pageNum
      }
      this.$request.get('/film/selectByPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    handleEdit(row) {
      this.editData = JSON.parse(JSON.stringify(row))
      this.editVisible = true

    },
    handleDelete(movieId) {
      this.$confirm('您确认删除该用户吗？', '确认删除', { type: "warning" }).then(response => {
        this.$request.delete('/film/delete/' + movieId).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => { })
    },

    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    handleAvatarSuccess(response, file, fileList) {
      this.editData.poster = response.data;
    },
    update() {   //保存当前的用户信息到数据库，并更新浏览器的本地存储
      if (!this.user.token) {
        this.$message.error('Token不存在或已过期，请重新登录。');
        return; // 提前返回，不执行后续代码
      }
      // 序列化数组为JSON字符串
      this.$request.put('/film/update', this.editData).then(res => {
        if (res.code == 200) {
          this.$message.success('保存成功');   //提示保存成功后
          this.editVisible = false;   //关闭编辑对话框
          this.load(1);   //重新加载数据
        } else {
          this.$message.error(res.msg);
        }
      }
      )

    }
  },
}
</script>

<style>
.el-card {
  height: 91.2vh;
}

.el-upload-dragger {
  width: 200px;
  height: 290px;
}

.el-form-item--mini.el-form-item,
.el-form-item--small.el-form-item {
  margin-bottom: 10px;
}
</style>