<template>
  <div>
    <div style="margin-bottom: 5px;">
      <el-input v-model="name" placeholder="请输入货物名" style="width: 200px;margin-left: 5px"
                suffix-icon="el-icon-search"
                @keyup.enter.native="loadPost"></el-input>
      <el-input v-model="storageId" placeholder="请输入仓库 ID" style="width: 200px;margin-left: 5px"
                suffix-icon="el-icon-search"
                @keyup.enter.native="loadPost"></el-input>
      <el-input v-model="category" placeholder="请输入类型" style="width: 200px;margin-left: 5px"
                suffix-icon="el-icon-search"
                @keyup.enter.native="loadPost"></el-input>
      <el-button style="margin-left: 10px;" type="primary" @click="handleSearch">查询</el-button>
      <el-button style="margin-left: 10px;" type="success" @click="resetParam">重置</el-button>
    </div>

    <el-table
        :data="tableData"
        height="600"
        style="width: 100%">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form class="demo-table-expand" inline label-position="left">
            <el-form-item label="货物名称："><span>{{ props.row.name }}</span></el-form-item>
            <el-form-item label="单位容量："><span>{{ props.row.unitVolume }}</span></el-form-item>
            <el-form-item label="货物 ID ："><span>{{ props.row.id }}</span></el-form-item>
            <el-form-item label="生产日期："><span>{{ }}</span></el-form-item>
            <el-form-item label="货物分类："><span>{{ props.row.category }}</span></el-form-item>
            <el-form-item label="保质期："><span>{{  }}</span></el-form-item>
            <el-form-item label="货物描述："><span>{{ props.row.notes }}</span></el-form-item>
            <el-form-item label="到期时间："><span>{{  }}</span></el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="货物名称" prop="name">
      </el-table-column>
      <el-table-column label="归属仓库" prop="storagename">
      </el-table-column>
      <el-table-column label="仓库 ID" prop="storageId">
      </el-table-column>
      <el-table-column label="数量" prop="quantity">
        <template slot-scope="scope">
          <span style="color: #050505">{{ scope.row.quantity }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <i class="el-icon-bottom-right" @click="input(scope.row)"></i>
          <i class="el-icon-top-right" @click="output(scope.row)"></i>
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

    <!-- 入库操作表单 -->
    <el-dialog :visible.sync="inDialogVisible" center title="入库提示" width="30%">
      <el-form ref="inForm" :model="inForm" label-width="80px">
        <el-form-item label="货品 ID">
          <el-col :span="20">
            <el-input v-model="inForm.goods" :disabled="true"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="仓库 ID">
          <el-col :span="20">
            <el-input v-model="inForm.storage" :disabled="true"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="操作员ID">
          <el-col :span="20">
            <el-input v-model="inForm.userid" :disabled="true"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="数量">
          <el-col :span="20">
            <el-input v-model="inForm.count" placeholder="请输入正整数"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注">
          <el-col :span="20">
            <el-input v-model="inForm.remark" placeholder="请输入操作备注" type="textarea"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveIn">入库</el-button>
      </span>
    </el-dialog>

    <!-- 出库操作表单 -->
    <el-dialog :visible.sync="outDialogVisible" center title="出库提示" width="30%">
      <el-form ref="outForm" :model="outForm" label-width="80px">
        <el-form-item label="货品 ID">
          <el-col :span="20">
            <el-input v-model="outForm.goods" :disabled="true"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="仓库 ID">
          <el-col :span="20">
            <el-input v-model="inForm.storage" :disabled="true"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="操作员ID">
          <el-col :span="20">
            <el-input v-model="outForm.userid" :disabled="true"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="数量">
          <el-col :span="20">
            <el-input v-model="outForm.count" placeholder="请输入正整数"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注">
          <el-col :span="20">
            <el-input v-model="outForm.remark" placeholder="请输入操作备注" type="textarea"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveOut">出库</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "GoodsControl",
  computed: {
    userId() {
      return this.$store.state.user ? this.$store.state.user.id : ''; //从user表中获取ID信息然后传递并禁止编辑
    },
  },
  data() {
    return {
      tableData: [],
      pageSize: 8,
      pageNum: 1,
      total: 0,
      name: '',
      storageId: '',
      category: '',
      inDialogVisible: false,
      outDialogVisible: false,
      inForm: {
        goods: '',
        userid: '',
        storage: '',
        count: '',
        remark: '',
        type: 1
      },
      outForm: {
        goods: '',
        userid: '',
        storage: '',
        count: '',
        remark: '',
        type: 2
      },
    }
  },
  methods: {
    clearForm() {
      this.inForm = {
        goods: '',
        userid: '',
        storage: '',
        count: '',
        remark: '',
        type: 1
      };
      this.outForm = {
        goods: '',
        userid: '',
        storage: '',
        count: '',
        remark: '',
        type: 2
      };
    },
    input(row) {
      this.clearForm();
      this.inForm.goods = row.id;
      this.inForm.storage = row.storageId;
      this.inForm.userid = this.userId; //设置当前操作员id为当前用户id，目的是确保当前用户操作信息被记录
      this.inForm.type = 1;
      this.inDialogVisible = true;
    },
    output(row) {
      this.clearForm();
      this.outForm.goods = row.id;
      this.inForm.storage = row.storageId;  //！！！这里不要随便改动表单形式，经测试这种用in的方式是最稳定的
      this.outForm.userid = this.userId;    //设置当前操作员id为当前用户id，目的是确保当前用户操作信息被记录
      this.outForm.type = 2;
      this.outDialogVisible = true;
    },
    // 入库操作
    saveIn() {
      if (this.validateInput(this.inForm)) {
        this.saveLog(this.inForm);
      } else {
        this.$message.error('请输入完整信息或信息输入格式错误');
      }
    },
    // 出库操作
    saveOut() {
      if (this.validateInput(this.outForm)) {
        this.saveLog({
          ...this.outForm,
          storage: this.tableData.find(item => item.id === this.outForm.goods)?.storageId
        });
        this.clearForm();
      } else {
        this.$message.error('请输入完整信息或信息输入格式错误');
      }
    },
    // 保存日志
    saveLog(log) {
      this.$axios.post(this.$httpUrl + '/log/save', log).then(res => res.data).then(res => {
        console.log(res)
        if (res.code == 200) {
          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.inDialogVisible = false;
          this.outDialogVisible = false;
          this.loadPost();
        } else {
          this.$message.error('操作失败');
          this.outDialogVisible = false;
        }
      });
    },
    validateInput(form) {
      // 手动进行表单验证
      if (!form.goods || !form.userid || !form.count || !form.remark || !/^[1-9]\d*$/.test(form.count) || form.count.length > 7) {
        return false;
      } else {
        return true;
      }
    },
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
    resetParam() {
      this.name = ''
      this.storageId = ''
      this.category = ''
      this.loadPost()
    },
    handleSearch() {
      this.pageNum = 1;
      this.loadPost();
    },
    loadPost() {
      this.$axios.post(this.$httpUrl + '/goods/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          name: this.name,
          storageId: this.storageId,  // 新增的查询条件
          category: this.category,  // 新增的查询条件
        }
      }).then(res => res.data).then(res => {
        console.log(res)
        if (res.code == 200) {
          this.tableData = res.data
          this.total = res.total
        } else {
          alert('获取数据失败')
        }
      })
    }
  },
  beforeMount() {
    this.loadPost()
  }
}
</script>

<style scoped>
.demo-table-expand {
  font-size: 0;
}

.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}

.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}

.el-icon-bottom-right {
  font-size: 32px;
  user-select: none;
  cursor: pointer;
  color: #599dfd;
}

.el-icon-top-right {
  margin-left: 10px;
  font-size: 32px;
  user-select: none;
  cursor: pointer;
  color: #f17b7b;
}
</style>