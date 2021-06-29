<template>
  <div class="app-container">
    <div class="btn-top">
      <el-button @click="createStep" type="primary" plain>新建</el-button>
      <div class="text-left-label" style="margin-left: 40px">
        <span>产品：</span>
        <el-select v-model="productId" @change="changeProduct" class="select-32" clearable>
          <el-option v-for="item in productList" :key="item.productName" :label="item.productName" :value="item.id">
            <span class="font-color-ed5454">{{ item.productName }}</span>
          </el-option>
        </el-select>
      </div>
      <div class="text-left-label" style="margin-left: 10px">
        <span>节点名称：</span>
        <el-input v-model="stepName" style="width: 150px"/>
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
          {{ scope.$index+1 }}
        </template>
      </el-table-column>
      <el-table-column label="产品名称" show-overflow-tooltip>
        <template slot-scope="scope">
          {{ scope.row.productName }}
        </template>
      </el-table-column>
      <el-table-column label="节点名称" show-overflow-tooltip>
        <template slot-scope="scope">
          {{ scope.row.stepName }}
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
      <el-table-column label="节点KEY" align="center">
        <template slot-scope="scope">
          {{ scope.row.stepCode }}
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center">
        <template slot-scope="scope">
          {{ scope.row.createTimeStr }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" fixed="right">
        <template slot-scope="scope">
          <a class="btn-link" @click="handleModify(scope.$index, scope.row)"
          >修改</a
          >
        </template>
      </el-table-column>
    </el-table>
    <el-pagination class="page" @size-change="handleSizeChange" @current-change="handleCurrentChange"
                   :current-page="currentPage" :page-sizes="[10, 20, 30, 50]" :page-size="pageSize"
                   layout=" sizes, prev, pager, next, jumper" :total="total"
    >
    </el-pagination>

    <!-- 节点添加改弹框 -->
    <el-dialog title="添加节点" :visible.sync="dialogAddVisible" width="433px" :center="true">
      <div>
        <span>产品名称：</span>
        <el-select v-model="stepAddData.productId" @change="changeProduct1" class="select-32" clearable>
          <el-option v-for="item in productList" :key="item.productName" :label="item.productName" :value="item.id">
            <span class="font-color-ed5454">{{ item.productName }}</span>
          </el-option>
        </el-select>
      </div>
      <div style="margin-top: 20px">
        <span>节点名称：</span>
        <el-input v-model="stepAddData.stepName" style="width: 300px"/>
      </div>
      <div style="text-align: center">
        <el-button type="primary" @click="sumbitAdd" class="input-top">提交</el-button>
      </div>
    </el-dialog>

    <!-- 节点修改改弹框 -->
    <el-dialog title="修改节点" :visible.sync="dialogModifyVisible" width="433px" :center="true">
      <div>
        <span>产品名称：</span>
        <el-input v-model="stepModifyData.productName" :disabled="true" style="width: 300px"/>
      </div>
      <div style="margin-top: 20px">
        <span>节点名称：</span>
        <el-input v-model="stepModifyData.stepName" style="width: 300px"/>
      </div>
      <div style="text-align: center">
       <el-button type="primary" @click="sumbitModify" class="input-top">提交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addStep, getProductSelectList, getStepList, modifyStep } from '@/api/table'

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
      productId: null,
      productList: [],
      dialogAddVisible: false,
      dialogModifyVisible: false,
      stepAddData: {
        productId: '',
        stepName: ''
      },
      stepModifyData: {
        id: '',
        productName: '',
        stepName: ''
      },
      stepName: ''
    }
  },
  created() {
    this.fetchData()
    getProductSelectList().then(response => {
      this.productList = response.data
    })
  },
  methods: {
    createStep() {
      this.dialogAddVisible = true
    },
    handleModify(index, row) {
      this.dialogModifyVisible = true
      this.stepModifyData.productName = row.productName
      this.stepModifyData.id = row.id
      this.stepModifyData.stepName = row.stepName
    },
    changeProduct: function(val) {
      this.productId = val
    },
    changeProduct1: function(val) {
      this.stepAddData.productId = val
    },
    search: function() {
      this.currentPage = 1
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
    sumbitAdd: function() {
      if (this.stepAddData.stepName === '') {
        this.$message({
          message: '节点名称不能为空',
          type: 'warning'
        })
        return false
      }
      if (this.stepAddData.productId === '') {
        this.$message({
          message: '请选择产品',
          type: 'warning'
        })
        return false
      }
      addStep(this.stepAddData).then(response => {
        if (response.code === 0) {
          this.dialogAddVisible = false
          this.fetchData()
        }
      })
    },
    sumbitModify: function() {
      if (this.stepModifyData.stepName === '') {
        this.$message({
          message: '节点名称不能为空',
          type: 'warning'
        })
        return false
      }
      modifyStep(this.stepModifyData).then(response => {
        if (response.code === 0) {
          this.dialogModifyVisible = false
          this.fetchData()
        }
      })
    },

    fetchData() {
      this.listLoading = true
      let reqData = {
        currentPage: this.currentPage,
        pageSize: this.pageSize,
        productId: this.productId,
        stepName: this.stepName
      }
      getStepList(reqData).then(response => {
        this.list = response.data.records
        this.total = response.data.total
        this.listLoading = false
      })
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

.btn-link {
  color: #2d8cf0;
}

.input-top {
  margin-top: 15px
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
