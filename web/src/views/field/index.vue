<template>
  <div class="app-container">
    <div class="btn-top">
      <el-button @click="createChain" type="primary" plain>新建</el-button>
      <div class="text-left-label" style="margin-left: 40px">
        <span>产品：</span>
        <el-select v-model="productId" @change="changeProduct" class="select-32" clearable>
          <el-option v-for="item in productList" :key="item.productName" :label="item.productName" :value="item.id">
            <span class="font-color-ed5454">{{ item.productName }}</span>
          </el-option>
        </el-select>
      </div>
      <div class="text-left-label" style="margin-left: 10px">
        <span>节点：</span>
        <el-select v-model="stepId" @change="changeStep" class="select-32" clearable>
          <el-option v-for="item in stepList" :key="item.stepName" :label="item.stepName" :value="item.id">
            <span class="font-color-ed5454">{{ item.stepName }}</span>
          </el-option>
        </el-select>
      </div>
      <div class="text-left-label" style="margin-left: 10px">
        <span>要素名：</span>
        <el-input v-model="keyword" style="width: 150px"/>
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
      <el-table-column label="节点名称" align="center" show-overflow-tooltip>
        <template slot-scope="scope">
          {{ scope.row.stepName }}
        </template>
      </el-table-column>
      <el-table-column label="要素英文名" align="center">
        <template slot-scope="scope">
          {{ scope.row.enName }}
        </template>
      </el-table-column>
      <el-table-column label="要素中文名" align="center">
        <template slot-scope="scope">
          {{ scope.row.chName }}
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center">
        <template slot-scope="scope">
          {{ scope.row.createTimeStr }}
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

    <!-- 证据要素修改弹框 -->
    <el-dialog title="证据要素修改" :visible.sync="dialogModifyVisible" width="433px" :center="true">
      <div>
        <span>要素中文名：</span>
        <el-input :value="fieldModifyData.chName" v-model="fieldModifyData.chName" style="width: 280px"/>
      </div>
      <div class="input-top">
        <span>要素英文名：</span>
        <el-input :value="fieldModifyData.enName" v-model="fieldModifyData.enName" style="width: 280px"/>
      </div>
      <div style="text-align: center">
       <el-button type="primary" @click="sumbitModify" class="input-top">提交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getFieldList, getProductSelectList, getStepSelectList, modifyField } from '@/api/table'

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
      productId: null,
      stepId: null,
      list: null,
      listLoading: true,
      productList: [],
      stepList: [],
      fieldModifyData: {
        'chName': '',
        'enName': '',
        'id': ''
      },
      dialogModifyVisible: false,
      keyword: ''
    }
  },
  created() {
    this.fetchData()
    getProductSelectList().then(response => {
      this.productList = response.data
    })
  },
  methods: {
    createChain() {
      this.$router.push('/field/list/create')
    },
    sumbitModify: function() {
      if (this.fieldModifyData.chName === '') {
        this.$message({
          message: '要素中文名不能为空',
          type: 'warning'
        })
        return false
      }
      if (this.fieldModifyData.enName === '') {
        this.$message({
          message: '要素英文名不能为空',
          type: 'warning'
        })
        return false
      }
      modifyField(this.fieldModifyData).then(response => {
        if (response.code === 0) {
          this.dialogModifyVisible = false
          this.fetchData()
        }
      })
    },
    changeProduct: function(val) {
      this.productId = val
      this.stepId = null
      this.stepList = []
      let reqData = {
        productId: val
      }
      getStepSelectList(reqData).then(response => {
        this.stepList = response.data
      })
    },
    handleModify(index, row) {
      this.dialogModifyVisible = true
      this.fieldModifyData.chName = row.chName
      this.fieldModifyData.enName = row.enName
      this.fieldModifyData.id = row.id
    },
    changeStep: function(val) {
      this.stepId = val
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

    fetchData() {
      this.listLoading = true
      let reqData = {
        currentPage: this.currentPage,
        pageSize: this.pageSize,
        productId: this.productId,
        stepId: this.stepId,
        keyword: this.keyword
      }
      getFieldList(reqData).then(response => {
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
