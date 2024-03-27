<template>
    <div class="register_page">
        <div class="register" style="display: flex;">
            <div class="login_welcome" style="flex: 1;padding-right: 100px;">
                <img src="../assets/image/login_welcome.png" style="height: 453px;width:615px ;">
            </div>
            <div style=" flex:1; display: flex;flex-direction: column;align-items: center; justify-content: center;">
                <img src="../assets/image/logo.png" style="padding-bottom: 15px;width: 260px;height: 85px;">
                <div class="register_box" style="display: flex; flex-direction: column;text-align: center;">
                    <div class="register_top">
                        <span>用户注册</span>
                    </div>
                    <div class="login_right_bottom" style="width: 304px; height:270px;margin: auto;margin-top: 27px;">
                        <el-form :model="user" :rules="rules" ref="RegisterRef">
                            <el-form-item prop="username">
                                <el-input prefix-icon="el-icon-user" placeholder="请输入手机账号" v-model="user.username"
                                    size="medium"></el-input>
                            </el-form-item>
                            <el-form-item prop="password">
                                <el-input prefix-icon="el-icon-lock" show-password placeholder="请输入密码"
                                    v-model="user.password" size="medium"></el-input>
                            </el-form-item>
                            <el-form-item prop="confirmpass">
                                <el-input prefix-icon="el-icon-lock" show-password placeholder="请再次输入密码"
                                    v-model="user.confirmpass" size="medium"></el-input>
                            </el-form-item>
                            <!-- 阅读条款 -->
                            <template>
                                <el-radio v-model="radio" style="margin-bottom: 10px;"><span
                                        style="font-size: 13px;">我已阅读并同意<a href="#"> 用户协议 </a>和<a href="#"> 隐私条款
                                        </a></span></el-radio>
                            </template>
                            <el-button class="login_button" type="primary" @click="register">注册</el-button>
                            <div class="register_prompt">还没有账号？请
                                <span style="color:#0f9876; cursor: pointer;" @click="$router.push('/login')">
                                    登录 </span>
                            </div>
                        </el-form>
                    </div>
                </div>
            </div>
        </div>

    </div>
</template>
<script>
export default {
    name: 'Register',
    components: {
    },
    data() {
        var validatePassword = (rule, confirmpass, callback) => {
            if (confirmpass === '') {
                callback(new Error('请再次输入密码'));
            } else if (confirmpass !== this.user.password) {
                callback(new Error('两次输入的密码不一致'));
            } else {
                callback();
            }
        };
        return {
            rules: {
                username: [
                    { required: true, message: '请输入账号', trigger: 'blur' },
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                ],
                confirmpass: [
                    { validator: validatePassword, required: true, trigger: 'blur' }
                ]
            }
        }
    },
    methods: {
        register() {
            this.$refs['RegisterRef'].validate((valid) => {
                if (valid) {
                    //验证通过输入账号和密码
                    this.$request.post('/register', this.user).then(res => {
                        if (res.code === '200') {
                            this.$router.push('/login')
                            this.$message.success('注册成功')
                            localStorage.setItem("token", JSON.stringify(res.data))
                        } else {
                            this.$message.error(res.msg);
                        }
                    }
                    )
                }
            })
        }


    }
} 
</script>
<style>
.register_page {
    height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #ecefff;
}

.register_box {
    flex: 1;
    width: 348px;
    height: 320px;
    background-color: #fff;
    border-radius: 12px 12px 20px 20px;
}

.register_top {
    width: 348px;
    height: 48px;
    background-color: #f5f5f6;
    border-radius: 12px 12px 0 0;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 20px;
    color: #26262B;
}

.login_button {
    width: 304px;
    height: 42px;
    font-size: 15px;
    border-radius: 8px;
    background: #446dff;
}

.el-input__inner {
    background: #2727290A;
    border-radius: 8px;
    border: 1px solid transparent;
}

.el-input__inner:focus {
    background-color: #fff;
}

.register_prompt {
    margin-top: 9px;
    font-size: 14px;
}</style>