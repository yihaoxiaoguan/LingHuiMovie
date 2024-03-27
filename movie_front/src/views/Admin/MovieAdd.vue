<template>
    <el-card>
        <el-form v-model="addfrom" :ref="addfrom" label-width="80px">
            <el-form-item label="电影封面">
                <el-upload class="upload-demo" 
                drag 
                accept=".png,.jpg" 
                :headers="{ token: user.token }"
                action="http://localhost:8081/file/upload"
                :on-success="handleAvatarSuccess"  
                multiple
                list-type="picture"
                style="display: flex;">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                </el-upload>
            </el-form-item>
            <el-form-item label="电影名称">
                <el-input v-model="addfrom.movieName" style="width: 300px;"></el-input>
            </el-form-item>
            <el-row>
                <el-col :span="5">
                    <el-form-item label="导演">
                        <el-input v-model="addfrom.director"></el-input>
                    </el-form-item>
                </el-col>   
                <el-col :span="8">
                    <el-form-item label="演员">
                        <el-input v-model="addfrom.actors"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-form-item label="电影类型">
                <el-input v-model="addfrom.type" style="width: 650px;"></el-input>
            </el-form-item>
            <el-row >
                <el-col :span="5">
                    <el-form-item label="制片地区">
                        <el-select v-model="addfrom.region" placeholder="请选择制片地区">
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
                </el-col>
                <el-col :span="4">
                    <el-form-item label="全片时长" style="padding-left: 10px;">
                        <el-input-number v-model="addfrom.duration" :min="10" :max="550">分钟</el-input-number>
                    </el-form-item>
                </el-col>
                <el-col :span="4">
                    <el-form-item label="上映时间" style="padding-left: 20px;">
                        <el-date-picker placeholder="选择日期" v-model="addfrom.releaseTime" style="width: 150px;"></el-date-picker>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-form-item label="剧情简介" style="width: 730px;">
                <el-input type="textarea" v-model="addfrom.introduction" autosize></el-input>
            </el-form-item>
            <div style="text-align: center;width: 700px;">
                    <el-button type="primary" @click="update">提交</el-button>
                </div>
        </el-form>
    </el-card>
</template>

<script>
export default {
    name: "MovieAdd",
    data() {
        return {
            addfrom: {
                movieName: "",
                releaseTime: "",
                type:"",
                director: "",
                actors: "",
                region: "",
                duration:120,
                poster: "", 
                introduction: "",
            
            }, //初始化表单数据   
            user: JSON.parse(localStorage.getItem('Moive_user') || '{}'), //从浏览器的本地存储中获取token（用户信息）, 
        }
    },
    methods: {

        handleAvatarSuccess(response, file, fileList) {
            this.addfrom.poster = response.data;
        },
        update() {   //保存当前的用户信息到数据库，并更新浏览器的本地存储
            if (!this.user.token) {
                this.$message.error('Token不存在或已过期，请重新登录。');
                return; // 提前返回，不执行后续代码
            } 
             // 序列化数组为JSON字符串
            this.$request.post('/film/add', this.addfrom).then(res => {
                if (res.code == 200) {
                    this.$message.success('保存成功');   //提示保存成功后
                    this.$router.push('/admin/MovieOnIn');   //跳转到电影列表页面
            } else {
                this.$message.error(res.msg);
            }
        }    
            )
        
}
    }
}
</script>

<style>
.el-card {
    height: 91.5vh;
}
</style>