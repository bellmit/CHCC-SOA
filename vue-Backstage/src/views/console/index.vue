<template>
    <el-row class="home" :gutter="10">
        <el-col :span="8">
            <el-card shadow="hover">
                <Boxcard/>
            </el-card>
            <el-card shadow="hover" style="margin-top:10px;">
                <RaddarChart/>
            </el-card>
            <el-card shadow="hover" style="margin-top:10px;">
                <BarChart/>
            </el-card>
        </el-col>

        <el-col :span="16">
            <div class="num">
                <el-card shadow="hover" :key="item">
                    <i class="icon"></i>
                    <div class="detail">
                        <p class="num1">$ 2323041.41</p>
                        <p class="txt">今日支付订单金额</p>
                    </div>
                </el-card>

                 <el-card shadow="hover" :key="item">
                    <i class="icon"></i>
                    <div class="detail">
                        <p class="num1">$ 45503.25</p>
                        <p class="txt">今日收益</p>
                    </div>
                </el-card>

                 <el-card shadow="hover" :key="item">
                    <i class="icon"></i>
                    <div class="detail">
                        <p class="num1">255 (件)</p>
                        <p class="txt">出货数量</p>
                    </div>
                </el-card>
            </div>

            <el-card shadow="hover">
                <div style="height:330px">
                    <LineChart :chart-data="lineChartData"/>
                </div>
            </el-card>

            <div class="graph">
                <el-col :span="14">
                    <el-card shadow="hover">
                    <BerChart/>
                    </el-card>
                </el-col>
               
               <el-col :span="12">
                <el-card shadow="hover">
                    <PieChart/>
                </el-card>
               </el-col>
            </div>
        </el-col>
    </el-row>
</template>

<script>
import PieChart from './components/PieChart'
import RaddarChart from './components/RaddarChart'
import BarChart from './components/BarChart'
import LineChart from './components/LineChart'
import Boxcard from './components/Boxcard'
import BerChart from './components/BerChart'

const lineChartData = {
  newVisitis: {
    proData: [200, 192, 120, 144, 160, 130, 140],
    devData: [180, 160, 151, 106, 145, 150, 130],
    expectedData: [100, 120, 161, 134, 105, 160, 165],
    actualData: [120, 82, 91, 154, 162, 140, 145]
  }
}

export default {
    components: {
        PieChart,
        RaddarChart,
        BarChart,
        LineChart,
        Boxcard,
        BerChart
    },
    data() {
        return {
            lineChartData: lineChartData.newVisitis
        }
    }
}
</script>

<style lang="scss" scoped>
    .home {
        .num {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
            .el-card {
                width: 32%;
                margin-bottom: 10px;
            }
        }

        .graph {
            margin-top: 10px;
            display: flex;
            justify-content: space-between;
        }
    }
</style>