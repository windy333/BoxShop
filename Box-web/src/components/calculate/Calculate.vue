<template>
  <div>
    <div class="container">
      <el-row>
        <el-col :span="12">
          <h2>单位容量计算器</h2>
          <el-form>
            <el-row>
              <el-col :span="8">
                <el-form-item label="物品体积">
                  <el-input v-model="volume" type="number"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8" style="margin-left: 50px">
                <el-form-item label="物品质量">
                  <el-input v-model="weight" type="number"></el-input>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="10">
                <el-form-item label="物品种类">
                  <el-select v-model="species" placeholder="请选择物品种类">
                    <el-option label="食品类" value="1"><span>食品类</span></el-option>
                    <el-option label="医药类" value="2"><span>医药类</span></el-option>
                    <el-option label="服装类" value="3"><span>服装类</span></el-option>
                    <el-option label="水果类" value="4"><span>水果类</span></el-option>
                    <el-option label="电子类" value="5"><span>电子类</span></el-option>
                    <el-option label="家电类" value="6"><span>家电类</span></el-option>
                    <el-option label="文具类" value="7"><span>文具类</span></el-option>
                    <el-option label="运动类" value="8"><span>运动类</span></el-option>
                    <el-option label="特殊类" value="9"><span>特殊类</span></el-option>
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item>
                  <el-button type="primary" @click="calculateUnitCapacity">计算</el-button>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <div v-if="result !== null">
                  <p>参考单位容量为: <span>{{ result.data }}</span></p>
                </div>
              </el-col>

            </el-row>

          </el-form>
          <div id="radar" class="chart1" style="margin-top: 30px"></div>
        </el-col>

        <el-col :span="12">
          <div class="alert-section">
            <h2>公告栏</h2>
            <el-alert show-icon style="margin: 10px;" title="欢迎体验单位容量计算器" type="warning"></el-alert>
            <el-alert show-icon style="margin: 10px;" title="请输入正确的格式和选择合适的种类，未知种类可按特殊类处理"
                      type="info"></el-alert>
            <h2 style="margin-top: 70px">智能化建设进度</h2>
            <div id="pie" class="chart2" style="margin-top: 20px"></div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts/core';
import {DatasetComponent, GridComponent, LegendComponent, TitleComponent, TooltipComponent} from 'echarts/components';
import {LineChart, PieChart, RadarChart} from 'echarts/charts';
import {LabelLayout} from 'echarts/features';
import {CanvasRenderer} from 'echarts/renderers';

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Calculate",
  mounted() {
    echarts.use([DatasetComponent, TooltipComponent, GridComponent, LegendComponent,
      LineChart, PieChart, CanvasRenderer,  LabelLayout, RadarChart,
      TitleComponent]);
    this.PieChart();
    this.RadarChart();
  },

  data() {
    return {
      volume: 0,
      weight: 0,
      species: null,
      result: null
    };
  },

  methods: {
    RadarChart() {
      let chartDom = document.getElementById('radar');
      let myChart = echarts.init(chartDom);
      let option;
      option = {
        title: {
          text: '2024 BoxShop 投资'
        },
        legend: {
          data: ['预估(万元)', '实际(万元)']
        },
        radar: {
          // shape: 'circle',
          indicator: [
            {name: '销售利润', max: 10000},
            {name: '仓库费用', max: 16000},
            {name: '科技投入', max: 30000},
            {name: '员工投入', max: 38000},
            {name: '门店宣传', max: 52000},
            {name: '货物销售', max: 25000}
          ]
        },
        series: [
          {
            name: 'Budget vs spending',
            type: 'radar',
            data: [
              {
                value: [4200, 14000, 20000, 35000, 50000, 18000],
                name: '预估(万元)'
              },
              {
                value: [5000, 14000, 28000, 26000, 42000, 21000],
                name: '实际(万元)'
              }
            ]
          }
        ]
      };
      myChart.setOption(option);
    },

    PieChart() {
      let chartDom = document.getElementById('pie');
      let myChart = echarts.init(chartDom);
      let option;
      option = {
        legend: {},
        tooltip: {
          trigger: 'axis',
          showContent: false
        },
        dataset: {
          source: [
            ['product', '2024', '2025', '2026', '2027', '2028', '2029'],
            ['武汉中心商店', 56.5, 60.6, 72.4, 83, 92.3, 100],
            ['加盟商店(广州)', 42.1, 51.4, 60.1, 72.3, 83.8, 100],
            ['东湖商店', 13.3, 22.7, 33.7, 45.6, 55.6, 60],
            ['西湖商店', 25.2, 35.1, 45.2, 55.4, 60, 70],
            ['BoxShop', 40, 50, 50, 70, 80, 85]
          ]
        },
        xAxis: {type: 'category'},
        yAxis: {gridIndex: 0},
        grid: {top: '55%'},
        series: [
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: {focus: 'series'}
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: {focus: 'series'}
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: {focus: 'series'}
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: {focus: 'series'}
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: {focus: 'series'}
          },
          {
            type: 'pie',
            id: 'pie',
            radius: '30%',
            center: ['50%', '25%'],
            emphasis: {
              focus: 'self'
            },
            label: {
              formatter: '{b}: {@2024} ({d}%)'
            },
            encode: {
              itemName: 'product',
              value: '2024',
              tooltip: '2024'
            }
          }
        ]
      };
      myChart.setOption(option);
    },

    calculateUnitCapacity() {
      if (this.species) {
        fetch(this.$httpUrl + '/calculateUnitCapacity', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            volume: this.volume,
            weight: this.weight,
            species: this.species
          })
        })
            .then(response => response.json())
            .then(data => {
              this.result = data;
            });
      } else {
        alert('请先选择物品种类！');
      }
    }
  },
}
</script>


<style scoped>
.chart1 {
  width: 650px;
  height: 400px;
}

.chart2 {
  width: 650px;
  height: 430px;
}
</style>