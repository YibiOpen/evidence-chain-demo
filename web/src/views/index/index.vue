<template>
  <div>
    <div class="main">
      <div class="index-top">
        <div class="content">
          <div class="logo">
            <i class="icon">EEC</i>基于区块链的电子存证管理平台
          </div>
          <div class="btn-right">
            <router-link to="/login">登录</router-link>
          </div>
        </div>
      </div>
      <div class="index-banner">
        <div class="slogan">
          <h3>Electronic Evidence Chain</h3>
          <div class="summary">基于区块链的电子存证管理平台</div>
        </div>
      </div>
      <div class="index-content">
        <div class="item">
          <div class="total">{{ signData.totalEviCount }}</div>
          <div class="title">存证统计</div>
        </div>
        <div class="item">
          <div class="total">{{ signData.totalChainCount }}</div>
          <div class="title">上链统计</div>
        </div>
      </div>
    </div>
    <div class="copyright">
      CopyRight ©2021 杭州亦笔科技有限公司
    </div>
  </div>
</template>

<script>
import { getTotalInfo } from "@/api/table";
export default {
  data() {
    return {
      signData: {
        totalEviCount: "",
        totalChainCount: ""
      }
    };
  },
  created() {
    this.getData();
  },
  methods: {
    getData() {
      getTotalInfo().then(res => {
        const { data } = res;
        this.signData = data;
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.main {
  min-height: calc(100vh - 57px);
}
.index-top {
  width: 100%;
  background: #fff;
  height: 62px;
  color: #454a5b;
  z-index: 99;
  width: 100%;
  min-width: 1280px;
  box-shadow: 0 2px 10px 0 rgb(0 0 0 / 5%);
  .content {
    width: 1100px;
    margin: 0 auto;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .logo {
      padding-top: 14px;
      display: flex;
      align-items: center;
      .icon {
        margin-right: 10px;
        display: flex;
        padding: 4px 5px;
        color: #fff;
        font-size: 16px;
        font-weight: 700;
        background: #195ff1;
        letter-spacing: 2px;
        border-radius: 3px;
      }
    }
    .btn-right {
      padding-top: 20px;
      font-size: 14px;
      .register {
        color: #195ff1;
        font-weight: 700;
      }
      span {
        padding: 0 8px;
      }
    }
  }
}
.index-banner {
  width: 100%;
  height: 440px;
  background: url("../../assets/images/banner-index.jpg") no-repeat top center;
  background-size: cover;
  .slogan {
    width: 1200px;
    margin: 0 auto;
    color: #fff;
    h3 {
      margin: 0;
      padding: 120px 0 0 40px;
      font-size: 36px;
    }
    .summary {
      padding: 20px 0 0 40px;
      font-size: 18px;
    }
  }
}
.index-content {
  position: relative;
  top: -60px;
  width: 1100px;
  height: 140px;
  margin: 0 auto;
  display: flex;
  overflow: hidden;
  background: #fff;
  box-shadow: 0 10px 20px 0 rgb(0 0 0 / 5%);
  border-radius: 4px;

  .item {
    width: 550px;
    text-align: center;
    .total {
      padding: 20px 0 10px 0;
      line-height: 42px;
      font-size: 42px;
      color: #ff8324;
    }
    .title {
      font-size: 18px;
    }
  }
  .item + .item {
    border-left: 1px solid #efefef;
  }
}
.copyright {
  padding: 20px 0;
  border-top: 1px solid #efefef;
  font-size: 12px;
  text-align: center;
  color: #454a5b;
}
</style>
