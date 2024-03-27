<template>
    <el-card>
        <div class="userManager">
            <div class="header" style="width: 100%;display: flex; padding: 15px 15px 20px 15px; ">
                <div>
                    <el-button type="danger" @click="deleteBatch">批量删除</el-button>
                </div>
                <div>
                    <el-input style="width: 200px" placeholder="查询用户名" v-model="username"></el-input>
                    <el-input style="width: 200px; margin: 0 5px" placeholder="查询姓名" v-model="phone"></el-input>
                    <el-button type="primary" @click="load(1)">查询</el-button>
                </div>
                <div>
                    <el-button type="primary" @click="handleAdd">新增</el-button>
                </div>

            </div>
            <div class="main">
                <el-table :data="tableData" stripe :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }"
                    @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="45"></el-table-column>
                    <el-table-column prop="id" label="ID" width="80px"></el-table-column>
                    <el-table-column label="头像">
                        <template v-slot="scope">
                            <div style="display: flex; align-items: center">
                                <el-image style="width: 50px; height: 50px; border-radius: 50%" v-if="scope.row.avatar"
                                    :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]"></el-image>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="username" label="用户名"></el-table-column>
                    <el-table-column prop="phone" label="手机号"></el-table-column>
                    <el-table-column prop="address" label="地址"></el-table-column>
                    <el-table-column label="操作">

                        <template v-slot="scope">
                            <el-button type="primary" @click="handleEdit(scope.row)" plain>编辑</el-button>
                            <el-button type="danger" @click="deleteUser(scope.row.id)" plain>删除</el-button>
                        </template>
                    </el-table-column>

                </el-table>
                <div style="margin: 10px 0;" align="center">
                    <el-pagination @current-change="handleCurrentChange" :current-page="pageNum" :page-size="pageSize"
                        layout="total, prev, pager, next" :total="total">
                    </el-pagination>
                </div>
            </div>
        </div>

        <el-dialog title="用户信息编辑" :visible.sync="addVisible">
            <el-form :model="addData" label-width="70px " style="width: 80%;" :rules="rules" ref="addref">
                <div style="margin: 15px; text-align: center;">
                    <el-upload class="avatar-uploader" action="http://localhost:8081/file/upload"
                        :headers="{ token: user.token }" :on-success="handleAvatarSuccess" :show-file-list="false">
                        <img v-if="addData.avatar" :src="addData.avatar" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </div>
                <el-form-item label="呢称">
                    <el-input v-model="addData.username"></el-input>
                </el-form-item>
                <el-form-item label="手机号" prop="phone">
                    <el-input v-model="addData.phone"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="addData.password" type="password"></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="addData.email"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="addData.address"></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>



    </el-card>
</template>

<script>
export default {
    name: 'UserManager',
    data() {
        return {
            tableData: [],  //用户数据
            pageNum: 1,     //当前页码
            pageSize: 5,   //每页显示多少条
            username: '',     //用户名
            phone: '',     //
            total: 0,       //总条数
            addVisible: false, //编辑对话框是否显示
            addData: {},
            user: JSON.parse(localStorage.getItem('Moive_user') || '{}'), //从浏览器的本地存储中获取token（用户信息）,
            rules: {
                phone: [
                    { required: true, message: '请输入手机号', trigger: 'blur' },
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                ],
            },
            ids: [] //批量删除的id数组
        }
    },
    created() {
        this.load()
    },
    methods: {
        deleteBatch() {
            if (!this.ids.length) {
                this.$message.warning('请选择数据')
                return
            }
            this.$confirm('您确认批量删除这些数据吗？', '确认删除', { type: "warning" }).then(response => {
                this.$request.delete('/user/delete/batch', { data: this.ids }).then(res => {
                    if (res.code === '200') {   // 表示操作成功
                        this.$message.success('操作成功')
                        this.load(1)
                    } else {
                        this.$message.error(res.msg)  // 弹出错误的信息
                    }
                })
            }).catch(() => { })
        },
        handleSelectionChange(rows) {   // 当前选中的所有的行数据
            this.ids = rows.map(v => v.id)
        },

        deleteUser(id) {
            this.$confirm('您确认删除该用户吗？', '确认删除', { type: "warning" }).then(response => {
                this.$request.delete('/user/delete/' + id).then(res => {
                    if (res.code === '200') {   // 表示操作成功
                        this.$message.success('操作成功')
                        this.load(1)
                    } else {
                        this.$message.error(res.msg)  // 弹出错误的信息
                    }
                })
            }).catch(() => { })
        },
        load(pageNum) {
            if (pageNum) {
                this.pageNum = pageNum
            }
            this.$request.get('/user/selectByPage', {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    username: this.username,
                    phone: this.phone
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total
            })
        },
        handleCurrentChange(pageNum) {
            this.load(pageNum)
        },
        handleAvatarSuccess(response, file, fileList) {
            this.addData.avatar = response.data;
        },
        handleAdd() {    //新增数据
            this.addData = {}  //新增用户时先清空表单
            this.addVisible = true
        },
        handleEdit(row) {
            this.addData = JSON.parse(JSON.stringify(row))
            this.addVisible = true

        },
        save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
            this.$refs.addref.validate((valid) => {
                if (valid) {
                    this.$request({
                        url: this.addData.id ? '/user/update' : '/user/add',
                        method: this.addData.id ? 'PUT' : 'POST',
                        data: this.addData
                    }).then(res => {
                        if (res.code === '200') {  // 表示成功保存
                            this.$message.success('保存成功')
                            this.load(1)
                            this.addVisible = false
                            this.$router.go(0)
                        } else {
                            this.$message.error(res.msg)  // 弹出错误的信息
                        }
                    })
                }
            })
        },
    }
}
</script>

<style>
.el-card {
    height: 91.2vh;
}

.el-input__inner {
    background: #4545480a;
    border: 1px solid transparent;
}

.el-input__inner:focus {
    background-color: #fff;
}

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