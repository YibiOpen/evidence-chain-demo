<template>
  <div class="app-container">
    <div class="btn-top">
      <el-button @click="createProduct" type="primary" plain>新建</el-button>
      <div class="text-left-label" style="margin-left: 20px">
        <span>产品名称：</span>
        <el-input v-model="productName" style="width: 150px"/>
      </div>
      <el-button @click="search" type="primary" plain style="margin-left: 10px">查询</el-button>
    </div>
    <el-table
        v-loading="listLoading"
        :data="list"
        element-loading-text="Loading"
    >
      <el-table-column align="center" label="序号">
        <template slot-scope="scope">
          {{ scope.$index +1}}
        </template>
      </el-table-column>
      <el-table-column label="产品名称" align="center" show-overflow-tooltip>
        <template slot-scope="scope">
          {{ scope.row.productName }}
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center">
        <template slot-scope="scope">
          <div v-if="scope.row.openStatus == '1'">
            <el-tag size="small">正常</el-tag>
          </div>
          <div v-else>
            <el-tag type="info" size="small">停用</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="产品KEY" align="center">
        <template slot-scope="scope">
          {{ scope.row.productCode }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" fixed="right">
        <template slot-scope="scope">
          <a class="btn-link" @click="handleModify(scope.$index, scope.row)">修改</a>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination class="page" @size-change="handleSizeChange" @current-change="handleCurrentChange"
                   :current-page="currentPage" :page-sizes="[10, 20, 30, 50]" :page-size="pageSize"
                   layout=" sizes, prev, pager, next, jumper" :total="total"
    >
    </el-pagination>

    <!--初始化 -->
    <el-dialog title="初始化" :visible.sync="dialogInitVisible" :before-close="modelClose" class="dialog-wrapper"
               width="433px" :center="true" :show-close="false"
    >
      <div>
        <span>是否使用国密：</span>
        <el-input v-model="chainInfo.encryptTypeStr" :disabled="true" style="width:200px"/>
      </div>
      <div style="margin-top: 10px">
        <span>FISCO-BCOS：</span>
        <el-input v-model="chainInfo.fiscoBcosVersion" :disabled="true" style="width: 200px"/>
      </div>
      <div style="margin-top: 10px">
        <span>WeBASE版本：</span>
        <el-input v-model="chainInfo.webaseVersion" :disabled="true" style="width: 200px"/>
      </div>
      <div style="text-align: center">
        <el-button type="primary" @click="deployContract" class="input-top">部署存证合约</el-button>
      </div>
    </el-dialog>

    <!-- 产品添加改弹框 -->
    <el-dialog title="添加产品" :visible.sync="dialogAddVisible" width="433px" :center="true">
      <div>
        <span>产品名称：</span>
        <el-input v-model="productAddData.productName" style="width: 300px"/>
      </div>
      <div style="text-align: center">
        <el-button type="primary" @click="sumbitAdd" class="input-top">提交</el-button>
      </div>
    </el-dialog>

    <!-- 产品修改改弹框 -->
    <el-dialog title="修改产品" :visible.sync="dialogModifyVisible" width="433px" :center="true">
      <div>
        <span>产品名称：</span>
        <el-input v-model="productModifyData.productName" style="width: 300px"/>
      </div>
      <div style="text-align: center">
        <el-button type="primary" @click="sumbitModify" class="input-top">提交</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { addProduct, deployContract, getChainInfo, getProductList, modifyProduct } from '@/api/table'
import { getUserId } from '@/utils/auth'

export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      currentPage: 1,
      pageSize: 10,
      total: 0,
      list: null,
      listLoading: true,
      dialogAddVisible: false,
      dialogModifyVisible: false,
      dialogInitVisible: false,
      chainInfo: {
        initStatus: false,
        encryptType: '',
        encryptTypeStr: '',
        fiscoBcosVersion: '',
        webaseVersion: ''
      },
      productAddData: {
        productName: ''
      },
      productModifyData: {
        id: '',
        productName: ''
      },
      productName: ''
    }
  },
  created() {
    getChainInfo().then(response => {
      this.chainInfo = response.data
      if (this.chainInfo.initStatus) {
        this.dialogInitVisible = false
      } else {
        this.dialogInitVisible = true
      }
      if (this.chainInfo.encryptType === 1) {
        this.chainInfo.encryptTypeStr = '国密'
      } else {
        this.chainInfo.encryptTypeStr = '非国密'
      }
    })
    this.fetchData()
  },
  methods: {
    createProduct() {
      this.dialogAddVisible = true
    },
    deployContract: function() {
      const userId = getUserId()
      let reqData = {
        userId: userId
      }
      deployContract(reqData).then(response => {
        if (response.code === 0) {
          this.$message({
            message: '合约部署成功',
            type: 'success'
          })
          this.dialogInitVisible = false
        }
      })
    },
    handleModify(index, row) {
      this.dialogModifyVisible = true
      this.productModifyData.productName = row.productName
      this.productModifyData.id = row.id
    },
    sumbitAdd: function() {
      if (this.productAddData.productName === '') {
        this.$message({
          message: '产品名称不能为空',
          type: 'warning'
        })
        return false
      }
      addProduct(this.productAddData).then(response => {
        if (response.code === 0) {
          this.dialogAddVisible = false
          this.fetchData()
        }
      })
    },
    sumbitModify: function() {
      if (this.productModifyData.productName === '') {
        this.$message({
          message: '产品名称不能为空',
          type: 'warning'
        })
        return false
      }
      modifyProduct(this.productModifyData).then(response => {
        if (response.code === 0) {
          this.dialogModifyVisible = false
          this.fetchData()
        }
      })
    },
    search: function() {
      this.currentPage = 1
      this.fetchData()
    },
    clearText: function() {
      this.fetchData()
    },
    handleSizeChange: function(val) {
      this.pageSize = val
      this.currentPage = 1
      this.fetchData()
    },
    handleCurrentChange: function(val) {
      this.currentPage = val
      this.fetchData()
    },

    fetchData() {
      this.listLoading = true
      let reqData = {
        currentPage: this.currentPage,
        pageSize: this.pageSize,
        productName: this.productName
      }
      getProductList(reqData).then(response => {
        this.list = response.data.records
        this.total = response.data.total
        this.listLoading = false
      })
    },
    modelClose: function() {
      this.$emit('close')
    }
  }
}
</script>
<style lang="scss">
.page {
  padding: 24px 0 24px 0;
  text-align: center;
}

.search-part-right {
  float: right;
  padding-right: 40px
}

.btn-top {
  padding-bottom: 20px;
  display: flex;
}

.input-top {
  margin-top: 15px
}

.btn-link {
  color: #2d8cf0;
}

.individer-line {
  padding: 0 10px;
  color: #c2c2c2;
}

.el-divider--horizontal {
  margin: 35px 0;
}

.el-table th {
  background: #f8f8f9;
  color: #515a6e;
}

.sign-box {
  .need-sign-title {
    margin-top: 20px;
  }

  .el-dialog__body {
    padding-bottom: 60px;
    padding-top: 0;
  }
}
</style>
