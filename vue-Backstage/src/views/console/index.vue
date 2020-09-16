<template>
    <el-row class="home" :gutter="20">
        <el-col :span="8">
            <el-card shadow="hover" style="height:550px">
                <Boxcard/>
            </el-card>
            <el-card shadow="hover" style="height:260px; margin-top:20px">
                鼠标悬浮时显示
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
                    <LineChart/>
                </div>
            </el-card>

            <div class="graph">
                <el-card shadow="hover">
                    <div style="height:280px">
                        <RaddarChart/>
                    </div>
                </el-card>
                <el-card shadow="hover">
                    <div style="height:280px">
                        <PieChart></PieChart>
                    </div>
                </el-card>
                <el-card shadow="hover">
                    <div style="height:280px">
                        <BarChart/>
                    </div>
                </el-card>
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

export default {
    components: {
        PieChart,
        RaddarChart,
        BarChart,
        LineChart,
        Boxcard
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
            .el-card {
                width: 33%;
            }
        }
    }
</style>