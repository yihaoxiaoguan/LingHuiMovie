<template>
    <div>
        <el-card>
            <el-form :model="user" label-width="70px " style="width: 80%;">
                <div style="margin: 15px; text-align: center;">
                    <el-upload
                        class="avatar-uploader"
                        action="http://localhost:8081/file/upload"
                        :headers="{token: user.token}"
                        :on-success="handleAvatarSuccess"
                        :show-file-list="false">
                        <img v-if="user.avatar" :src="user.avatar" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                </div>
                <el-form-item label="呢称">
                    <el-input v-model="user.username"></el-input>
                </el-form-item>
                <el-form-item label="手机号" >
                    <el-input v-model="user.phone" disabled></el-input>
                </el-form-item>
                <el-form-item label="密码" >
                    <el-input v-model="user.password" type="password" disabled></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="user.email"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="user.address"></el-input>
                </el-form-item>
                <div style="text-align: center;">
                    <el-button type="primary" @click="update">保存</el-button>
                </div>

            </el-form>

        </el-card>

    </div>
</template>

<script>
export default {
    name: 'Personal_Update',
    data() {
        return {
            user: JSON.parse(localStorage.getItem('Moive_user') || '{}') //从浏览器的本地存储中获取token（用户信息）
        }
    },
    created() {
    },
    methods: {
        handleAvatarSuccess(response, file,fileList){
            console.log(response);
            this.user.avatar = response.data;
        },
        update() {   //保存当前的用户信息到数据库，并更新浏览器的本地存储
            if (!this.user.token) {
                this.$message.error('Token不存在或已过期，请重新登录。');
                return; // 提前返回，不执行后续代码
            }
            this.$request.put('/user/update', this.user).then(res => {
                if (res.code == 200) {
                    this.$message.success('保存成功');   //提示保存成功后
                    this.$router.push('/personal/PerInfo');//跳转到个人中心页面
                    localStorage.setItem('Moive_user', JSON.stringify(this.user)); //更新浏览器的本地存储
                    this.$emit('update:user', this.user); //更新父组件中的用户信息')
                          
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
 .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 50%;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 88px;
    height: 88px;
    line-height: 88px;
    text-align: center;
  }
  .avatar {
    width: 88px;
    height: 88px;
    display: block;


  }                        
                        

</style>