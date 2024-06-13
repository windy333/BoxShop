<template>
  <div>
    <div class="container">
      <el-row>
        <el-col :span="12">
          <h2 style="padding: 5px;">单位容量计算器</h2>
          <el-form>
            <el-row>
              <el-col :span="10">
                <el-form-item label="物品体积">
                  <el-input v-model="volume" type="number"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="2222" style="margin-left: 4%">
                <el-form-item label="物品种类" label-position="top">
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
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="物品质量">
                  <el-input v-model="weight" type="number"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8" style="margin-left: 4%">
                <el-form-item label="参考单位容量">
                  <el-input v-model="result.data" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item>
                  <i class="el-icon-cpu" style="cursor: pointer;font-size: 35px;margin-left: 500px;" 
                    @click="calculateUnitCapacity">
                  </i>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-col>

        <el-col :span="12">
          <div class="alert-section">
            <h2 style="padding: 5px;">公告栏</h2>
            <el-alert style="margin: 10px;" title="最开始是看到各种酷炫优雅的前端界面非常心动而常常
            怀疑自己， 但现在前端开发好像是最惨的了~ε=(´ο｀*)))唉，临近尾声了。学Java真是49年入国军，今
            天终于下定决心走CPP的开发了， 换个赛道跑也许会有些机会吧。这个Java项目从轮子开始一点点造，个
            人还是有很多的想法想加进去，但是时间和实力都来不及 补充了。最后将这个作品仅留在这作为一个曾经
            的纪念~" type="warning" :closable="false">
            </el-alert>
            <h2 style="margin-top: 30px">销售利润预测</h2>
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
import {LineChart, PieChart} from 'echarts/charts';
import {LabelLayout} from 'echarts/features';
import {CanvasRenderer} from 'echarts/renderers';

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Calculate",
  mounted() {
    echarts.use([DatasetComponent, TooltipComponent, GridComponent, LegendComponent,
      LineChart, PieChart, CanvasRenderer,  LabelLayout,TitleComponent]);
    this.PieChart();
  },

  data() {
    return {
      volume: 0,
      weight: 0,
      species: null,
      result: { data: '' } // 初始化 result 为包含空字符串的对象
    };
  },

  methods: {
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
        })
        .catch(error => {
          console.error('Error:', error);
        });
      } else {
        alert('请先选择物品种类！');
      }
    }
  },
}
</script>


<style scoped>
.chart2 {
  width: 650px;
  height: 430px;
}
</style>