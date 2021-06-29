<template>
  <div class="app-container">
    <div class="btn-top">
      <div class="text-left-label">
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
        <span>用户名：</span>
        <el-input v-model="userName" style="width: 150px"/>
      </div>
      <el-button @click="searchData" type="primary" plain style="margin-left: 10px">查询</el-button>
    </div>
    <el-table
        v-loading="listLoading"
        :data="list"
        element-loading-text="Loading"
    >
      <el-table-column align="center" label="序号" width="60px">
        <template slot-scope="scope">
          {{ scope.$index+1 }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="产品&节点名称" show-overflow-tooltip width="180px">
        <template slot-scope="scope">
          {{ scope.row.productName }}|{{ scope.row.stepName }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="用户名称" show-overflow-tooltip>
        <template slot-scope="scope">
          {{ scope.row.userName }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="用户证件" width="220px">
        <template slot-scope="scope">
          {{ scope.row.identType }}({{ scope.row.identNo }})
        </template>
      </el-table-column>
      <el-table-column label="存证时间" align="center">
        <template slot-scope="scope">
          {{ scope.row.saveTimeStr }}
        </template>
      </el-table-column>
      <el-table-column label="上链状态" align="center">
        <template slot-scope="scope">
          <div v-if="scope.row.chainStatus == '1'">
            <el-tag size="small">已上链</el-tag>
          </div>
          <div v-else>
            <el-tag type="info" size="small">未上链</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" fixed="right">
        <template slot-scope="scope">
          <a class="btn-link" @click="handlePreview(scope.$index, scope.row)">查看</a>
          <span class="individer-line">|</span>
          <a class="btn-link" @click="handleValid(scope.$index, scope.row)">核验</a>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination class="page" @size-change="handleSizeChange" @current-change="handleCurrentChange"
                   :current-page="currentPage" :page-sizes="[10, 20, 30, 50]" :page-size="pageSize"
                   layout=" sizes, prev, pager, next, jumper" :total="total"
    >
    </el-pagination>

    <!-- 证据查看弹框 -->
    <el-dialog title="证据详情信息" :visible.sync="dialogPreviewVisible" :center="true">
      <el-table :data="previewData">
        <el-table-column
            property="chName"
            label="证据要素名"
            width=""
        ></el-table-column>
        <el-table-column
            property="enValue"
            width=""
            label="证据要素值"
            show-overflow-tooltip
        ></el-table-column>
      </el-table>

      <el-table :data="previewFile">
        <el-table-column
            property="fileName"
            label="附件名"
            width=""
        ></el-table-column>
        <el-table-column
            property="chainAddress"
            width=""
            label="附件链上地址"
            show-overflow-tooltip
        ></el-table-column>
      </el-table>
    </el-dialog>

    <!-- 证据核验弹框 -->
    <el-dialog title="证据核验信息" :visible.sync="dialogValidVisible" :center="true">
      <div>
        <span>存证数据内容：</span>
        <el-input :value="mainData.dataJson" style="width: 400px" :readonly="true"/>
      </div>
      <div class="input-top">
        <span>存证上链地址：</span>
        <el-input :value="mainData.chainAddress" style="width: 300px" :disabled="true"/>
      </div>
      <div class="input-top">
        <span>链上存证数据hash算法：</span>
        <el-input :value="mainData.hashCal" style="width: 150px" :disabled="true"/>
      </div>
      <div class="input-top">
        <span>链上存证数据hash：</span>
        <el-input :value="mainData.dataHash" style="width: 400px" :readonly="true"/>
      </div>
      <div class="input-top">
        <span>链上存证时间：</span>
        <el-input :value="mainData.saveTime" style="width: 200px" :disabled="true"/>
      </div>
      <el-table :data="validFile" style="margin-top: 20px">
        <el-table-column
            property="fileName"
            label="附件名"
            width=""
        ></el-table-column>
        <el-table-column
            property="chainAddress"
            width=""
            label="附件链上地址"
            show-overflow-tooltip
        ></el-table-column>
        <el-table-column
            property="attachHash"
            width=""
            label="附件hash"
            show-overflow-tooltip
        ></el-table-column>
        <el-table-column
            property="hashCal"
            width=""
            label="hash算法"
            show-overflow-tooltip
        ></el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import { getEviDataList, getProductSelectList, getStepSelectList, validChain, previewChain } from '@/api/table'

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
      userName: null,
      list: null,
      listLoading: true,
      previewData: [],
      previewFile: [],
      validFile: [],
      dialogPreviewVisible: false,
      dialogValidVisible: false,
      productList: [],
      stepList: [],
      mainData: {}
    }
  },
  created() {
    this.fetchData()
    getProductSelectList().then(response => {
      this.productList = response.data
    })
  },
  methods: {
    handleValid(index, row) {
      let reqData = {
        dataId: row.id
      }
      validChain(reqData).then(response => {
        this.dialogValidVisible = true
        this.mainData = response.data
        this.validFile = response.data.fileValidRespList
      })
    },
    handlePreview(index, row) {
      let reqData = {
        dataId: row.id
      }
      previewChain(reqData).then(response => {
        this.dialogPreviewVisible = true
        this.previewData = response.data.dataPreviewRespList
        this.previewFile = response.data.filePreviewRespList
      })
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
    searchData: function() {
      this.fetchData()
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

    changeStep: function(val) {
      this.stepId = val
    },

    fetchData() {
      this.listLoading = true
      let reqData = {
        currentPage: this.currentPage,
        pageSize: this.pageSize,
        productId: this.productId,
        stepId: this.stepId,
        userName: this.userName
      }
      getEviDataList(reqData).then(response => {
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

.text-left-label {
  display: inline;
}

.btn-top {
  padding-bottom: 20px;
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
