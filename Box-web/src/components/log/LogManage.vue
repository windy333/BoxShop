<template>
  <div>

    <div style="text-align: center; font-size: 26px; margin-bottom: 20px; font-weight: bold;">
      <span>货物管理日志</span>
      <i class="el-icon-loading" style="user-select: none; margin-left: 5px;"></i>
      <i class="el-icon-upload2" style="user-select: none; cursor: pointer; float: right;" @click="exportExcel"></i>
    </div>

    <el-table
        ref="filterTable"
        :data="tableData"
        :default-sort="{prop: 'createtime', order: 'descending'}"
        :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
        height="580"
        style="width: 100%;"
    >
      <el-table-column column-key="date" label="操作时间" prop="createtime" sortable width="200">
        <template slot-scope="scope">
          {{ formatDate(scope.row.createtime) }}
        </template>
      </el-table-column>
      <el-table-column label="物品" prop="goodsname" width="120">
      </el-table-column>
      <el-table-column label="物品ID" prop="goods" width="100">
      </el-table-column>
      <el-table-column :filter-method="filterStorageMethod" :filters="filterStorage" filter-placement="bottom-end"
                       label="归属仓库"
                       prop="storagename" width="120">
      </el-table-column>
      <el-table-column label="仓库 ID" prop="storage" width="100">
      </el-table-column>
      <el-table-column label="操作员" prop="username" width="140">
        <template slot-scope="scope">
          <span style="color: #256bce">{{ scope.row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审批代码" prop="adminname" width="140">
      </el-table-column>
      <el-table-column label="数量" prop="count" width="100">
      </el-table-column>
      <el-table-column :filter-method="filterType" :filters="[{ text: '入库', value: 1 }, { text: '出库', value: 2 }]"
                       filter-placement="bottom-end"
                       label="操作类型"
                       prop="type" width="120">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type === 1" type="success">入库</el-tag>
          <el-tag v-else-if="scope.row.type === 2" type="danger">出库</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="remark">
      </el-table-column>


    </el-table>
    <div style="margin-left: 330px">
      <el-pagination
          :current-page="pageNum"
          :page-size="pageSize"
          :page-sizes="[8,100,500]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange">
      </el-pagination>
    </div>

  </div>
</template>

<script>
import ExcelJS from 'exceljs';

export default {
  name: "LogManage",
  data() {
    return {
      tableData: [],
      pageSize: 100,
      pageNum: 1,
      total: 0,
      filteredType: [],
      filterStorage: [],
    }
  },
  methods: {
    filterType(value, row) {
      return row.type === value;
    },
    filterStorageMethod(value, row) {
      return row.storagename === value;
    },
    formatDate(date) {
      const formattedDate = new Date(date);
      const year = formattedDate.getFullYear();
      const month = (1 + formattedDate.getMonth()).toString().padStart(2, '0');
      const day = formattedDate.getDate().toString().padStart(2, '0');
      const hours = formattedDate.getHours().toString().padStart(2, '0');
      const minutes = formattedDate.getMinutes().toString().padStart(2, '0');
      const seconds = formattedDate.getSeconds().toString().padStart(2, '0');
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
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
    loadPost() {
      this.$axios.post(this.$httpUrl + '/log/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
      }).then(res => {
        if (res.data.code === 200) {
          this.tableData = res.data.data;
          this.total = res.data.total;

          // 生成筛选项，去除重复数据(因为这里是从log中获取所以会有很多重复的数据)
          const uniqueStorageFilter = Array.from(new Set(res.data.data.map(storage => storage.storagename))).map(storagename => ({
            text: storagename,
            value: storagename
          }));
          this.filterStorage = uniqueStorageFilter;

        } else {
          alert('获取数据失败');
        }
      }).catch(error => {
        console.error('请求错误', error);
      });
    },

    exportExcel() {
      // 创建工作簿
      const workbook = new ExcelJS.Workbook();

      // 按年份和月份分组数据
      const groupedByYearMonth = this.tableData.reduce((acc, row) => {
        const date = this.formatDate(row.createtime).slice(0, 7); // YYYY-MM
        if (!acc[date]) {
          acc[date] = [];
        }
        acc[date].push(row);
        return acc;
      }, {});

      // 遍历每个年份和月份的数据
      Object.keys(groupedByYearMonth).forEach(yearMonth => {
        // 添加工作表
        const worksheet = workbook.addWorksheet(yearMonth);

        // 添加表头
        const headers = ['操作时间', '物品', '物品ID', '归属仓库', '仓库ID', '操作员', '审批代码', '数量', '操作类型', '备注'];
        worksheet.addRow(headers);

        // 物品数量统计对象
        const summary = {};

        // 添加数据行
        groupedByYearMonth[yearMonth].forEach(row => {
          const data = [
            this.formatDate(row.createtime),
            row.goodsname,
            row.goods,
            row.storagename,
            row.storage,
            row.username,
            row.adminname,
            row.count,
            row.type === 1 ? '入库' : '出库',
            row.remark
          ];
          worksheet.addRow(data);

          // 统计物品数量
          const key = `${row.goodsname}-${row.type === 1 ? '入库' : '出库'}`;
          if (!summary[key]) {
            summary[key] = 0;
          }
          summary[key] += parseInt(row.count, 10);
        });


        // 添加统计行
        worksheet.addRow([]);
        worksheet.addRow(['月统计记录']);
        Object.keys(summary).forEach(key => {
          const [goods, goodsname, type] = key.split('-');
          const total = summary[key];
          worksheet.addRow([goods, goodsname, type, total]);
        });
      });

      // 导出文件
      workbook.xlsx.writeBuffer().then(buffer => {
        const blob = new Blob([buffer], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});
        const fileName = 'Log.xlsx';

        // 创建下载链接
        const link = document.createElement('a');
        link.href = URL.createObjectURL(blob);
        link.download = fileName;

        // 触发下载
        link.click();

        // 清理链接对象
        URL.revokeObjectURL(link.href);
      });
    }

  },
  beforeMount() {
    this.loadPost()
  }
}
</script>

<style scoped>

</style>