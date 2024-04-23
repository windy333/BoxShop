<template>
  <div>
    <div style="margin-bottom: 5px;">
      <el-input v-model="name" placeholder="请输入货物名" style="width: 200px;margin-left: 5px"
                suffix-icon="el-icon-search"
                @keyup.enter.native="loadPost"></el-input>
      <el-input v-model="storageId" placeholder="请输入仓库ID代码" style="width: 200px;margin-left: 5px"
                suffix-icon="el-icon-search"
                @keyup.enter.native="loadPost"></el-input>
      <el-input v-model="category" placeholder="请输入类型" style="width: 200px;margin-left: 5px"
                suffix-icon="el-icon-search"
                @keyup.enter.native="loadPost"></el-input>
      <el-button style="margin-left: 10px;" type="primary" @click="handleSearch">查询</el-button>
      <el-button style="margin-left: 10px;" type="success" @click="resetParam">重置</el-button>
      <el-button style="margin-left: 10px;" type="primary" @click="add">新增</el-button>
    </div>

    <el-table
        :data="tableData"
        border
        height="600"
        style="width: 100%">
      <el-table-column fixed label="物品" prop="name" width="130">
      </el-table-column>
      <el-table-column label="物品编号" prop="id" width="80">
      </el-table-column>
      <el-table-column label="价格" prop="price" width="80">
        <template slot-scope="scope">
          {{ formatPrice(scope.row.price) }}
        </template>
      </el-table-column>
      <el-table-column label="归属仓库" prop="storagename" width="120">
      </el-table-column>
      <el-table-column label="仓库ID" prop="storageId" width="80">
      </el-table-column>
      <el-table-column label="类型" prop="category" width="100">
      </el-table-column>
      <el-table-column label="数量" prop="quantity" width="100">
        <template slot-scope="scope">
          <span style="color: #256bce">{{ scope.row.quantity }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="notes" width="240">
      </el-table-column>
      <el-table-column label="单位容量" prop="unitVolume" width="100">
      </el-table-column>
      <el-table-column label="总容量" prop="totalVolume" width="100">
      </el-table-column>
      <el-table-column label="操作" prop="operate">
        <template slot-scope="scope">
          <i class="el-icon-edit" style="font-size: 32px;user-select: none;cursor: pointer;color: #171616"
             @click="mod(scope.row)"></i>
          <el-popconfirm
              style="margin-left: 10px;"
              title="确定删除吗？"
              @confirm="del(scope.row.id)"
          >
            <el-icon slot="reference" name="close" style="font-size: 32px;user-select: none;cursor: pointer;margin-left: 10px;
                     color: #f17b7b" type="danger"
                     @click="del(scope.row.id)"></el-icon>
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
        :visible.sync="addDialogVisible"
        center
        title="新增物品"
        width="30%">
      <el-form ref="addForm" :model="addForm" :rules="addRules" label-width="80px">
        <el-form-item label="物品" prop="name">
          <el-col :span="20">
            <el-input v-model="addForm.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-col :span="20">
            <el-input v-model="addForm.price"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="仓库ID" prop="storageName">
          <el-col :span="20">
            <el-input v-model="addForm.storageId" placeholder="请匹配已存在的仓库"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="类型" prop="category">
          <el-col :span="20">
            <el-input v-model="addForm.category"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
          <el-col :span="20">
            <el-input v-model="addForm.quantity"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注" prop="notes">
          <el-col :span="20">
            <el-input v-model="addForm.notes" type="textarea"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="单位容量" prop="unitVolume">
          <el-col :span="20">
            <el-input v-model="addForm.unitVolume"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="doAdd">确定</el-button>
      </span>
    </el-dialog>

    <el-dialog
        :visible.sync="modDialogVisible"
        center
        title="编辑物品信息"
        width="30%">
      <el-form ref="modForm" :model="modForm" :rules="modRules" label-width="80px">
        <el-form-item label="物品" prop="name">
          <el-col :span="20">
            <el-input v-model="modForm.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-col :span="20">
            <el-input v-model="modForm.price"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="仓库ID" prop="storageName">
          <el-col :span="20">
            <el-input v-model="modForm.storageId" placeholder="请匹配已存在的仓库"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="类型" prop="category">
          <el-col :span="20">
            <el-input v-model="modForm.category"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
          <el-col :span="20">
            <el-input v-model="modForm.quantity"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注" prop="notes">
          <el-col :span="20">
            <el-input v-model="modForm.notes" type="textarea"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="单位容量" prop="unitVolume">
          <el-col :span="20">
            <el-input v-model="modForm.unitVolume"></el-input>
          </el-col>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="modDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="doMod">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "GoodsManage",
  data() {
    return {
      tableData: [],
      pageSize: 8,
      pageNum: 1,
      total: 0,
      name: '',
      storageId: '',
      category: '',
      addDialogVisible: false,
      modDialogVisible: false,
      //新增表单
      addForm: {
        id: '',
        name: '',
        price: '',
        storageId: null,
        category: '',
        quantity: '',
        notes: '',
        unitVolume: '',
        totalVolume: '',
      },
      //编辑表单
      modForm: {
        id: '',
        name: '',
        price: '',
        storageId: '',
        category: '',
        quantity: '',
        notes: '',
        unitVolume: '',
        totalVolume: '',
      },
      addRules: {
        name: [{required: true, message: '请输入物品名', trigger: 'blur'}],
        storageId: [{required: true, message: '请输入仓库ID', trigger: 'blur'}]
      },
      modRules: {
        name: [{required: true, message: '请输入物品名', trigger: 'blur'}],
        storageId: [{required: true, message: '请输入仓库ID', trigger: 'blur'}]
      }
    }
  },
  methods: {
    formatPrice(price) {
      return parseFloat(price).toFixed(2);
    },
    add() {
      this.addDialogVisible = true;
      this.$nextTick(() => {
        this.resetAddForm();
      });
    },
    del(id) {
      this.$prompt('请输入“确认删除”以确认删除操作', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /确认删除/,
        inputErrorMessage: '输入内容不正确'
      }).then(({value}) => {
        if (value === '确认删除') {
          this.$axios.get(this.$httpUrl + '/goods/del?id=' + id).then(res => {
            console.log(res);
            if (res.data.code == 200) {
              this.$message.success('操作成功！');
              this.loadPost();
            } else {
              this.$message.error('操作失败！');
            }
          }).catch(() => {
            this.$message.info('网络错误，删除操作失败');
          });
        } else if (value !== null) {
          this.$message.info('取消删除操作或输入内容不正确');
        } else {
          this.$message.info('取消了删除操作');
        }
      }).catch(() => {
        this.$message.info('取消了删除操作');
      });
    },
    mod(row) {
      this.modDialogVisible = true;
      this.$nextTick(() => {
        this.modForm.id = row.id
        this.modForm.name = row.name
        this.modForm.price = row.price
        this.modForm.storageId = row.storageId
        this.modForm.category = row.category
        this.modForm.quantity = row.quantity
        this.modForm.notes = row.notes
        this.modForm.unitVolume = row.unitVolume
      });
    },
    resetAddForm() {
      this.$refs.addForm.resetFields();
    },
    resetModForm() {
      this.$refs.modForm.resetFields();
    },
    doAdd() {
      this.$axios.post(this.$httpUrl + '/goods/save', this.addForm).then(res => res.data).then(res => {
        console.log(res)
        if (res.code == 200) {
          this.$message({
            message: '新增成功！',
            type: 'success'
          });
          this.addDialogVisible = false;
          this.loadPost();
          this.resetAddForm();
        } else {
          this.$message({
            message: '新增失败！',
            type: 'error'
          });
        }
      });
    },
    doMod() {
      this.$axios.post(this.$httpUrl + '/goods/update', this.modForm).then(res => res.data).then(res => {
        console.log(res)
        if (res.code == 200) {
          this.$message({
            message: '编辑成功！',
            type: 'success'
          });
          this.modDialogVisible = false;
          this.loadPost();
          this.resetModForm();
        } else {
          this.$message({
            message: '编辑失败！',
            type: 'error'
          });
        }
      });
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
    handleSearch() { //查询必须在已用页查询（否则会页面停留在较后的页，不显示数据）
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

</style>