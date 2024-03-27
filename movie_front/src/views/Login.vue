<template>
    <div class="login_page">
        <div class="login" style="display: flex;">
            <div class="login_welcome" style="flex: 1;padding-right: 100px;">
                <img src="../assets/image/login_welcome.png" style="height: 453px;width:615px ;">
            </div>
            <div style=" flex:1; display: flex;flex-direction: column;align-items: center; justify-content: center;">
                <img src="../assets/image/logo.png" style="padding-bottom: 15px;width: 260px;height: 85px;">
                <div class="login_box" style="display: flex; flex-direction: column;text-align: center;">
                    <el-tabs :stretch="true" class="login_box_top">
                        <el-tab-pane label="用户登录">
                            <div class="UserLogin" style=" width: 304px; height:270px;margin: auto;margin-top: 10px;">
                                <el-form :model="user" :rules="rules" ref="LoginUserRef">
                                    <el-form-item prop="username">
                                        <el-input prefix-icon="el-icon-user" placeholder="请输入手机账号" v-model="user.username"
                                            size="medium"></el-input>
                                    </el-form-item>
                                    <el-form-item prop="password">
                                        <el-input prefix-icon="el-icon-lock" show-password placeholder="请输入密码"
                                            v-model="user.password" size="medium"></el-input>
                                    </el-form-item>
                                    <el-form-item prop="code">
                                        <div style="display: flex;">
                                            <el-input placeholder="请输入验证码" style="flex: 1;" size="medium"
                                                v-model="user.code"></el-input>
                                            <div style="flex: 1;">
                                                <ValidCode @update:value="getCode" style="height: 36px;"></ValidCode>
                                                <!--引入验证码组件  -->
                                            </div>
                                        </div>
                                    </el-form-item>
                                    <!-- 阅读条款 -->
                                    <template>
                                        <el-radio v-model="radio" style="margin-bottom: 10px;" label="1"><span
                                                style="font-size: 13px;">我已阅读并同意<a href="#"> 用户协议 </a>和<a href="#"> 隐私条款
                                                </a></span></el-radio>
                                    </template>
                                    <el-button class="login_button" type="primary" @click="Userlogin">登录</el-button>
                                    <div class="login_prompt">还没有账号？请<span style="color:#0f9876; cursor: pointer;"
                                            @click="$router.push('/register')"> 注册 </span></div>
                                </el-form>
                            </div>
                        </el-tab-pane>
                        <el-tab-pane label="管理员登录">
                            <div class="AdminLogin" style=" width: 304px; height:270px;margin: auto;margin-top: 10px;">
                                <el-form :model="user" :rules="rules" ref="LoginAdminRef">
                                    <el-form-item prop="username">
                                        <el-input prefix-icon="el-icon-user" placeholder="请输入管理员账号" v-model="user.username"
                                            size="medium"></el-input>
                                    </el-form-item>
                                    <el-form-item prop="password">
                                        <el-input prefix-icon="el-icon-lock" show-password placeholder="请输入密码"
                                            v-model="user.password" size="medium"></el-input>
                                    </el-form-item>
                                </el-form>
                                <template>
                                    <el-radio v-model="radio" style="margin-bottom: 10px;"><span
                                            style="font-size: 13px;">我已阅读并同意<a href="#"> 用户协议 </a>和<a href="#"> 隐私条款
                                            </a></span></el-radio>
                                </template>
                                <el-button class="login_button" type="primary" @click="Adminlogin">登录</el-button>
                            </div>
                        </el-tab-pane>
                    </el-tabs>

                </div>
            </div>
        </div>

    </div>
</template>
<script>
import ValidCode from '@/components/ValidCode.vue';
export default {
    name: 'login_page',
    components: {
        ValidCode
    },
    data() {
        var validateCode = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入验证码'));
            } else if (value !== this.code) {
                callback(new Error('验证码错误'));
            } else {
                callback();
            }
        };
        return {
            code: '', //验证码组件传过来的值
            user: {
                name: '',
                code: '',
                password: '',
                radio: '1'
            },
            rules: {
                username: [
                    { required: true, message: '请输入账号', trigger: 'blur' },
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                ],
                code: [
                    { validator: validateCode, trigger: 'blur' }
                ],
            }
        }
    },
    methods: {
        getCode(code) {
            console.log(code);
            this.code = code;
        },
        Userlogin() {
            this.$refs['LoginUserRef'].validate((valid) => {
                if (valid) {
                    //验证通过输入账号和密码
                    this.$request.post('/login', this.user).then(res => {
                        if (res.code === '200') {
                            this.$router.push('/home')
                            this.$message.success('登录成功')
                            localStorage.setItem("Moive_user", JSON.stringify(res.data))
                        } else {
                            this.$message.error(res.msg);
                        }
                    }
                    )
                }
            })
        },
        Adminlogin() {
            this.$refs['LoginAdminRef'].validate((valid) => {
                if (valid) {
                    //验证通过输入账号和密码
                    this.$request.post('/login', this.user).then(res => {
                        if (res.code === '200' ) {
                            if (res.data.role === '管理员') {        
                            this.$router.push('/Admin')
                            this.$message.success('登录成功')
                            localStorage.setItem("Moive_user", JSON.stringify(res.data))
                            }else{
                                this.$message.error('您不是管理员，无权访问')
                            }
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
.login_page {
    height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #ecefff;
}

.login_box {
    flex: 1;
    width: 348px;
    height: 320px;
    background-color: #fff;
    border-radius: 14px 14px 20px 20px;
}

.login_button {
    width: 304px;
    height: 42px;
    font-size: 15px;
    border-radius: 8px;
    background: #446dff;

}

.el-tabs__item {
    padding: 10px 0px 10px 0px;
    height: 49px;
    box-sizing: border-box;
    line-height: 28px;
    display: inline-block;
    list-style: none;
    font-size: 20px;
    font-weight: 500;
    color: #303133;
    position: relative;
}

.el-tabs__item {
    background-color: #F5F5F6;
    border-radius: 14px 14px 0px 0px;
    color: #25262B5C;
}

.el-tabs__item.is-top.is-active {
    background-color: #fff;
    color: #25262B;
}

.el-input__inner {
    background: #2727290A;
    border-radius: 8px;
    border: 1px solid transparent;
}

.el-input__inner:focus {
    background-color: #fff;
}

.login_button {
    height: 42px;
    background-image: linear-gradient(129.12deg, #446dff 0%, rgba(99, 125, 255, 0.75) 100%) !important;
    border-radius: 10px;
    backdrop-filter: blur(24px);
}

.login_prompt {
    margin-top: 9px;
    font-size: 14px;
}</style>