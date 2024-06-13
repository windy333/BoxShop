<template>
  <div>

    <div>
      <el-carousel :interval="10000" height="240px" indicator-position="none" type="card">
        <el-carousel-item>
          <img alt="Image 1" src="../assets/banner1.webp"
               style="width: 100%; object-fit: cover;">
        </el-carousel-item>
        <el-carousel-item>
          <img alt="Image 2" src="../assets/banner2.jpg"
               style="width: 100%; object-fit: cover;">
        </el-carousel-item>
        <el-carousel-item>
          <img alt="Image 3" src="../assets/banner3.webp"
               style="width: 100%; object-fit: cover;">
        </el-carousel-item>
      </el-carousel>
    </div>

    <div class="container">
      <div id="main" class="chart"></div>
      <div id="scatter" class="chart"></div>
    </div>

  </div>
</template>

<script>
import * as echarts from 'echarts/core';
import {BarChart, ScatterChart} from 'echarts/charts';
import {GridComponent, TitleComponent, TooltipComponent} from 'echarts/components';
import {CanvasRenderer} from 'echarts/renderers';
import axios from 'axios';

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Home',
  created() {
    echarts.use([TitleComponent, TooltipComponent, GridComponent, BarChart, CanvasRenderer, ScatterChart, ]);
    this.getBarchart();
    this.getScatterchart();
  },
  data() {
    return {
      chartData: {
        names: [],
        totalCapacities: [],
        remainingCapacities: []
      },
      scatterData: {
        names: [],
        quantities: [],
        storageIds: []
      }
    };
  },
  methods: {
    getBarchart() {
      axios.post(this.$httpUrl + '/storage/listPage', {
        //获取的data
      })
          .then(response => {
            const res = response.data;
            if (res.code === 200) {
              this.chartData.names = res.data.map(item => item.name);
              this.chartData.totalCapacities = res.data.map(item => item.initialCapacity);
              this.chartData.remainingCapacities = res.data.map(item => item.remainingCapacity);
              this.renderChart();
            } else {
              alert('获取数据失败');
            }
          })
          .catch(error => {
            console.error('Error fetching data', error);
            alert('获取数据失败');
          });
    },
    renderChart() {
      var myChart = echarts.init(document.getElementById('main'));
      let option = {
        title: {
          text: '商店仓库容量预警'
        },
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: this.chartData.names
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '总容量',
            type: 'bar',
            data: this.chartData.totalCapacities
          },
          {
            name: '剩余容量',
            type: 'bar',
            data: this.chartData.remainingCapacities,
            itemStyle: {
              normal: {
                color: function (params) {
                  // 剩余容量小于50w时显示red
                  if (params.value < 500000) {
                    return '#d64343';
                  }
                  return '#2dc15e';
                }
              }
            }
          }
        ]
      }
      myChart.setOption(option);
    },

    getScatterchart() {
      axios.get(this.$httpUrl + '/goods/listAll', {
        //获取的data
      })
          .then(response => {
            const res = response.data;
            if (res.code === 200) {
              this.scatterData.names = res.data.map(item => item.name);
              this.scatterData.quantities = res.data.map(item => item.quantity);
              this.scatterData.storageIds = res.data.map(item => item.storageId);
              this.renderScatterChart();
            } else {
              alert('获取数据失败');
            }
          })
          .catch(error => {
            console.error('Error fetching data', error);
            alert('获取数据失败');
          });
    },

    renderScatterChart() {
      this.$nextTick(() => {
        var scatterChart = echarts.init(document.getElementById('scatter'));

        //用一个空对象存储每个物品的归属仓库id
        const storageIdMap = {};

        let option = {
          title: {
            text: '货物数量堆积图'
          },
          tooltip: {
            trigger: 'item',
            formatter: function (params) {
              // 查找当前项
              const storageId = storageIdMap[params.name];
              return '物品: ' + params.name + '<br/>' + '数量: ' + params.value[1] +
                  '<br/>' + '归属仓库：' + storageId;
            }
          },
          xAxis: {
            show: false
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              type: 'scatter',
              data: this.scatterData.quantities.map((quantity, index) => {
                const name = this.scatterData.names[index];
                const storageId = this.scatterData.storageIds[index];

                storageIdMap[name] = storageId;

                return {
                  name: name,
                  value: [Math.random(), quantity]
                };
              }),
              symbolSize: function (value) {
                // 根据散点的值来计算散点的大小，限制在一个合理范围内
                return Math.min(Math.sqrt(value[1]) * 5, 40);
              },
              itemStyle: {
                color: function () {
                  // 随机颜色
                  return `hsl(${Math.random() * 360}, 50%, 50%)`;
                }
              }
            }
          ]
        };
        scatterChart.setOption(option);
      });
    }

  }
};
</script>

<style>
.container {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  width: 100%;
}

.chart {
  width: 48%; /* 设置图表宽度 */
  height: 380px;
  margin-top: 60px;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}
</style>