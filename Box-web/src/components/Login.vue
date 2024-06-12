<template>
  <div class="container">
    <div class="login-wrapper">
      <h1 class="header">BoxShop</h1>

      <el-form ref="loginForm" :model="loginForm"
               :rules="rules" label-width="100px">
        <el-form-item label="账号" prop="no">
          <el-input v-model="loginForm.no" autocomplete="off" size="small"
                    style="width: 200px" type="text"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password" autocomplete="off" show-password
                    size="small" style="width: 200px" type="password" @keyup.enter.native="confirm">

          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button :disabled="confirm_disabled" type="primary" @click="confirm">确 定</el-button>
        </el-form-item>
      </el-form>
      <p class="msg">Guess what's in the box ?<a href="#" @click="showSignUpMessage"> Sign up</a></p>

    </div>
  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Login",
  data() {
    return {
      confirm_disabled: false,
      loginForm: {
        no: '',
        password: ''
      },

      rules: {
        no: [
          {required: true, message: '请输入账号', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输密码', trigger: 'blur'}
        ],
      }

    }
  },
  methods: {
    showSignUpMessage() {
      alert('请联系相关负责人或管理员获取账号');
    },
    confirm() {
      this.confirm_disabled = true;
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.$axios.post(this.$httpUrl + '/user/login', this.loginForm).then(res => res.data).then(res => {
            console.log(res)
            if (res.code === 200) {
              this.$store.commit('setUser', res.data.user);
              this.$store.commit('setMenu', res.data.menu);
              console.log(res.data.menu);
              this.$router.replace('/Index');
              this.$nextTick(() => {
                this.$message({
                  showClose: true,
                  message: '登录成功',
                  type: 'success',
                  duration: 1000  // 1秒后自动关闭
                });
              });
            } else {
              this.confirm_disabled = false;
              if (res.code === 401) { // 网络或服务器问题,后期测试上传到服务器使用的
                this.$message.error({
                  message: '登录失败，错误代码：401',
                  duration: 1000
                });
              } else {
                this.$message.error({
                  message: '登录失败，用户名或密码错误！',
                  duration: 1000
                });
              }
            }
          });
        } else {
          this.confirm_disabled = false;
          this.$message.error({
            message: '校验失败',
            duration: 1500
          });
        }
      });
    }

  }
};
</script>

<style scoped>
.container {
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  height: 100vh;
  background-image: linear-gradient(to right, #fbc2eb, #a6c1ee);
}
.login-wrapper {
  background-color: #fff;
  width: 358px;
  height: 588px;
  border-radius: 15px;
  padding: 0 50px;
  user-select: none; /* 不可选中文本 */
}
.header {
  font-size: 38px;
  font-weight: bold;
  text-align: center;
  line-height: 200px;
  margin-left: 10px;
}
.el-input {
  width: 70%;
  outline: none;
}
.el-button {
  width: 40%;
  margin-left: 38px;
  margin-top: 10px;
  border-radius: 20px;
  background-image: linear-gradient(to right, #a6c1ee, #fbc2eb);
}
.msg {
  text-align: center;
  top: -20px;
  margin-left: 13px;
  line-height: 88px;
  position: relative;
}

a {
  text-decoration-line: none;
  color: #abc1ee;
}
</style>
