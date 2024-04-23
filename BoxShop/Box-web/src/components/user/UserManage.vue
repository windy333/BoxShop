<template>
  <div>
    <div style="margin-bottom: 5px;">
      <!--  输入框查询  v-model监视  回车事件 -->
      <el-input v-model="name" placeholder="请输入姓名" style="width: 200px;" suffix-icon="el-icon-search"
                @keyup.enter.native="handleSearch"></el-input>
      <el-select v-model="sex" filterable placeholder="请选择性别" style="margin-left: 5px;">
        <el-option
            v-for="item in sexs"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button style="margin-left: 10px;" type="primary" @click="handleSearch">查询</el-button>
      <el-button style="margin-left: 10px;" type="success" @click="resetParam">重置</el-button>
      <el-button style="margin-left: 10px;" type="primary" @click="add">新增</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
              borde>
      <el-table-column label="ID" prop="id" width="100">
      </el-table-column>
      <el-table-column label="账号" prop="no" width="140">
      </el-table-column>
      <el-table-column label="姓名" prop="name" width="160">
      </el-table-column>
      <el-table-column label="年龄" prop="age" width="120">
      </el-table-column>
      <el-table-column label="性别" prop="sex" width="120">
        <!--tag选项标签的应用（没有性别就不显示标签）-->
        <template slot-scope="scope">
          <template v-if="scope.row.sex !== null">
            <el-tag
                :type="scope.row.sex === 1 ? 'primary' : 'success'"
                disable-transitions>
              {{ scope.row.sex === 1 ? '男' : '女' }}
            </el-tag>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="权限" prop="roleId" width="150">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.roleId === 0 ? 'danger' : (scope.row.roleId === 1 ? 'primary' : 'success')"
              disable-transitions>{{
              scope.row.roleId === 0 ? '超级管理员' :
                  (scope.row.roleId === 1 ? '管理员' : '用户')
            }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="联系方式" prop="phone" width="300">
      </el-table-column>

      <el-table-column label="操作" prop="operate">
        <template slot-scope="scope">
          <i class="el-icon-edit-outline" style="font-size: 32px;user-select: none;cursor: pointer;color: #050505"
             @click="mod(scope.row)"></i>

          <el-popconfirm
              style="margin-left: 10px;"
              title="确定删除吗？"
              @confirm="del(scope.row.id)"
          >
            <el-icon slot="reference" name="switch-button"
                     style="font-size: 32px;user-select: none;cursor: pointer;margin-left: 10px;color: #f17b7b"
                     type="danger"
                     @click="del(scope.row.id)"
            ></el-icon>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-left: 330px">
      <el-pagination
          :current-page="pageNum"
          :page-size="pageSize"
          :page-sizes="[1,4,8]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange">
      </el-pagination>
    </div>

    <el-dialog
        :visible.sync="centerDialogVisible"
        center
        title="添加新的用户"
        width="30%">
      <!-- form表单的应用 -->
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="账号" prop="no">
          <el-col :span="20">
            <el-input v-model="form.no"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="名字" prop="name">
          <el-col :span="20">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-col :span="20">
            <!-- Type属性设置为password -->
            <el-input v-model="form.password" type="password"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-col :span="20">
            <el-input v-model="form.age"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.sex">
            <el-radio label="1">男</el-radio>
            <el-radio label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="联系方式" prop="phone">
          <el-col :span="20">
            <el-input v-model="form.phone"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="save">确 定</el-button>
    <el-button @click="centerDialogVisible = false">取 消</el-button>
  </span>
    </el-dialog>


    <!-- 编辑用户信息弹窗 -->
    <el-dialog
        :visible.sync="editDialogVisible"
        center
        title="编辑用户信息"
        width="30%"
    >
      <el-form ref="editForm" :model="editForm" :rules="editRules" label-width="80px">
        <el-form-item label="账号" prop="no">
          <el-col :span="20">
            <el-input v-model="editForm.no" disabled></el-input> <!-- 账号行disabled属性表示不能修改 -->
          </el-col>
        </el-form-item>
        <el-form-item label="名字" prop="name">
          <el-col :span="20">
            <el-input v-model="editForm.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-col :span="20">
            <!-- Type属性设置为password -->
            <el-input v-model="editForm.password" type="password"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-col :span="20">
            <el-input v-model="editForm.age"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="editForm.sex">
            <el-radio label="1">男</el-radio>
            <el-radio label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="联系方式" prop="phone">
          <el-col :span="20">
            <el-input v-model="editForm.phone"></el-input>
          </el-col>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doMod">保存</el-button>
        <el-button @click="editDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "UserManage",
  data() {

    let checkAge = (rule, value, callback) => {
      if (value > 999) {
        callback(new Error('年龄输入过大'));
      } else {
        callback();
      }
    };

    let checkDuplicate = (rule, value, callback) => {
      if (this.form.id) {
        return callback();
      }
      this.$axios.get(this.$httpUrl + "/user/findByNo?no=" + this.form.no).then(res => res.data).then(res => {
        if (res.code != 200) {
          callback()
        } else {
          callback(new Error('账号已经存在'));
        }
      })
    };

    return {
      tableData: [],
      pageSize: 8,
      pageNum: 1,
      total: 0,
      name: '',
      sex: '',
      sexs: [
        {
          value: '1',
          label: '男'
        }, {
          value: '0',
          label: '女'
        }
      ],

      centerDialogVisible: false,
      form: {
        id: '',
        no: '',
        name: '',
        password: '',
        age: '',
        phone: '',
        sex: '1',
        roleId: '2'  //新增默认为用户
      },

      editDialogVisible: false, // 新增的edit表单功能
      editForm: {
        id: '',
        no: '',
        name: '',
        password: '',
        age: '',
        phone: '',
        sex: '',
        roleId: '',
      },

      rules: {
        no: [
          {required: true, message: '请输入账号', trigger: 'blur'},
          {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'},
          {validator: checkDuplicate, trigger: 'blur'}
        ],
        name: [
          {required: true, message: '请输入名字', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'}
        ],
        age: [
          {required: true, message: '请输入年龄', trigger: 'blur'},
          {min: 1, max: 3, message: '长度在 1 到 3 个位', trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,3}$/, message: '年龄必须为正整数字', trigger: "blur"},
          {validator: checkAge, trigger: 'blur'}
        ],
      },

      editRules: {
        name: [
          {required: true, message: '请输入名字', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'}
        ],
        age: [
          {required: true, message: '请输入年龄', trigger: 'blur'},
          {min: 1, max: 3, message: '长度在 1 到 3 个位', trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,3}$/, message: '年龄必须为正整数字', trigger: "blur"},
          {validator: checkAge, trigger: 'blur'}
        ],
      },
    }
  },
  methods: {

    resetForm() {
      //重置表单
      this.$refs.form.resetFields();
    },

    del(id) {
      // 弹出确认框并验证指定信息
      this.$prompt('请输入“确认删除”以确认删除操作', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /确认删除/,             // 验证规则，必须输入“确认删除”
        inputErrorMessage: '输入内容不正确'
      }).then(({value}) => {
        if (value === '确认删除') {
          // 输入符合要求，执行删除操作
          this.$axios.get(this.$httpUrl + '/user/del?id=' + id).then(res => res.data).then(res => {
            //console.log(res)
            if (res.code === 200) {
              this.$message.success('操作成功！');
              this.loadPost();
            } else {
              this.$message.error('操作失败！');
            }
          }).catch(() => {
            this.$message.info('网络错误，删除操作失败');
          });
        } else if (value !== null) {
          // 取消操作或输入错误的内容
          this.$message.info('取消删除操作或输入内容不正确');
        } else {
          // 点击取消按钮
          this.$message.info('取消了删除操作');
        }
      }).catch(() => {
        // 点击取消按钮
        this.$message.info('取消了删除操作');
      });
    },

    doMod() {
      this.$axios.post(this.$httpUrl + '/user/update', this.editForm)
          .then(res => res.data)
          .then(res => {
            console.log(res);
            if (res.code === 200) {
              this.$message({
                message: '操作成功！',
                type: 'success'
              });
              this.loadPost();
              this.$refs.editForm.resetFields(); // 重置表单
              this.editDialogVisible = false;

            } else {
              this.$message({
                message: '操作失败！',
                type: 'error'
              });
            }
          });
    },

    mod(row) {
      //console.log(row);
      this.editDialogVisible = true;
      this.$nextTick(() => {
        this.resetEditForm(); // 重置编辑用户表单
        this.editForm.id = row.id;
        this.editForm.no = row.no;
        this.editForm.name = row.name;
        this.editForm.password = row.password;
        this.editForm.age = row.age + '';
        this.editForm.sex = row.sex + '';
        this.editForm.phone = row.phone;
        this.editForm.roleId = row.roleId;
      });

    },

    doSave() {
      this.$axios.post(this.$httpUrl + '/user/save', this.form).then(res => res.data).then(res => {
        //console.log(res)
        if (res.code == 200) {
          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.centerDialogVisible = false
          this.loadPost()
          this.resetForm()
        } else {
          this.$message({
            message: '操作失败！',
            type: 'error'
          });
        }
      })
    },

    save() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id) {
            this.doMod();
          } else {
            this.doSave();
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });

    },

    add() {
      this.centerDialogVisible = true
      //异步，关闭表单时应将保存的信息删除
      this.$nextTick(() => {
        this.resetForm()
      })
    },

    resetParam() {
      //重置方法
      this.name = ''
      this.sex = ''
      this.loadPost()
    },

    resetEditForm() {
      const defaultForm = {
        id: '',
        no: '',
        name: '',
        password: '',
        age: '',
        phone: '',
        sex: '',
        roleId: '',
      };
      Object.assign(this.editForm, defaultForm);
      this.$refs.editForm.resetFields();
    },

    //分页显示
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageNum = 1
      this.pageSize = val
      this.loadPost()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val
      this.loadPost()
    },
    handleSearch() {
      this.pageNum = 1;
      this.loadPost();
    },
    loadPost() {
      //可以简单理解为刷新页面获取数据
      this.$axios.post(this.$httpUrl + '/user/listPageC1', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          name: this.name,
          sex: this.sex
        }
      }).then(res => {
        console.log(res.data);
        this.tableData = res.data.data;
        this.total = res.data.total;
      })
          .catch(error => {
            console.error(error);
          });
    }
  },
  beforeMount() {
    this.loadPost();
  }
}

</script>

<style scoped>

</style>