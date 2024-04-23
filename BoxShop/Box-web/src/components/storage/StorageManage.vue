<template>
  <div>
    <div style="margin-bottom: 5px;">
      <el-input v-model="name" placeholder="请输入仓库名" style="width: 200px;" suffix-icon="el-icon-search"
                @keyup.enter.native="loadPost"></el-input>
      <el-button style="margin-left: 10px;" type="primary" @click="handleSearch">查询</el-button>
      <el-button style="margin-left: 10px;" type="success" @click="resetParam">重置</el-button>

      <el-popover
          content="仓库名一经确认，无法随意改变！且禁止删除已有的仓库！添加或操作仓库请联系维护员或责任人。
          修改仓库初始容量后需要更新对应仓库物品信息才能更新仓库容量"
          placement="bottom"
          style="margin-left: 66%"
          trigger="click"
          width="200">
        <el-button slot="reference">关于</el-button>
      </el-popover>

    </div>
    <el-table
        :data="tableData"
        stripe
        style="width: 100%">

      <el-table-column label="仓库代码" prop="id" width="150">
      </el-table-column>
      <el-table-column label="仓库名" prop="name" width="220">
      </el-table-column>
      <el-table-column label="备注" prop="remark" width="200">
      </el-table-column>
      <el-table-column label="初始容量" prop="initialCapacity" width="140">
      </el-table-column>
      <el-table-column label="统计容量" prop="calculatedCapacity" width="140">
      </el-table-column>
      <el-table-column label="剩余容量" prop="remainingCapacity" width="140">
        <template slot-scope="scope">
          <span :style="getRemainingCapacityStyle(scope.row)">{{ scope.row.remainingCapacity }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" prop="operate">
        <template slot-scope="scope">
          <i class="el-icon-edit-outline" style="font-size: 32px;user-select: none;cursor: pointer;"
             @click="mod(scope.row)"></i>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        :current-page="pageNum"
        :page-size="pageSize"
        :page-sizes="[1,4,8]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange">
    </el-pagination>

    <el-dialog
        :visible.sync="centerDialogVisible"
        center
        title="编辑仓库信息"
        width="30%">

      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="仓库ID" prop="id">
          <el-col :span="20">
            <el-input v-model="form.id" :disabled="true"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-col :span="20">
            <el-input v-model="form.remark" type="textarea"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="初始容量" prop="initialCapacity">
          <el-col :span="20">
            <el-input v-model="form.initialCapacity"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "StorageManage",
  data() {
    return {
      tableData: [],
      pageSize: 8,
      pageNum: 1,
      total: 0,
      name: '',
      centerDialogVisible: false,
      form: {
        id: '',
        remark: '',
        initialCapacity: ''
      },
    }
  },
  methods: {
    getRemainingCapacityStyle(row) {
      if (row.remainingCapacity < 0.4 * row.initialCapacity) {
        return {
          color: 'red'
        };
      } else {
        return {
          color: 'skyblue'
        };
      }
    },
    resetForm() {
      this.$refs.form.resetFields();
    },
    mod(row) {
      this.centerDialogVisible = true
      this.$nextTick(() => {
        //赋值到表单
        this.form.id = row.id
        this.form.remark = row.remark
        this.form.initialCapacity = row.initialCapacity
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
          console.log('操作失败');
          return false;
        }
      });

    },
    doSave() {
      this.$axios.post(this.$httpUrl + '/storage/save', this.form).then(res => res.data).then(res => {
        console.log(res)
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
    doMod() {
      this.$axios.post(this.$httpUrl + '/storage/update', this.form).then(res => res.data).then(res => {
        console.log(res)
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
      this.loadPost()
    },
    handleSearch() {
      this.pageNum = 1;
      this.loadPost();
    },
    loadPost() {
      this.$axios.post(this.$httpUrl + '/storage/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          name: this.name
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

</style>