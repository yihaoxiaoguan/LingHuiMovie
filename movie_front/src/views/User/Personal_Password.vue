<template>
  <div>
    <el-card>
        <el-form :model="user" ref="formRef" label-width="80px" :rules="rules" style="width: 80%;">
            <el-form-item label="旧密码" prop="oldPassword">
                <el-input type="password" v-model="user.oldPassword"  placeholder="请输入旧密码"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
                <el-input type="password" v-model="user.newPassword"  placeholder="请输入新密码"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmpass">
                <el-input type="password" v-model="user.confirmpass"  placeholder="请再次输入密码"></el-input>
            </el-form-item>
            <div style="text-align: center;">           
                <el-button type="primary" @click="update">提交</el-button>
            </div> 
        </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
    name: 'Register',
    data() {
        var validatePassword = (rule, confirmpass, callback) => {
    if (confirmpass === '') {
        callback(new Error('请再次输入密码'));
    } else if (confirmpass !== this.user.newPassword) {
        callback(new Error('两次输入的密码不一致'));
    } else {
        callback();
    }
};

        return {
            user: JSON.parse(localStorage.getItem('Moive_user') || '{}') ,//从浏览器的本地存储中获取token（用户信息）
            rules: {
                oldPassword: [
                    { required: true, message: '请输入旧密码', trigger: 'blur' },
                ],
                newPassword: [
                    { required: true, message: '请输入新密码', trigger: 'blur' },
                ],
                confirmpass: [
                    { validator: validatePassword, required: true,trigger: 'blur' },
                ],
            }
        }        
        
    },
    methods: {
        update() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.user.password = this.user.newPassword
          // 保存当前的用户信息到数据库
          this.$request.put('/user/update', this.user).then(res => {
            if (res.code === '200') {
              // 成功更新
              this.$message.success('修改成功')
              // 清除token
              localStorage.removeItem('Moive_user')
              // 跳转到登录页
              this.$router.push('/login')
            } else {
              this.$message.error(res.msg)
                        }
                    })
                }
                
            }
        )}
    }
}
</script>

<style>

</style>