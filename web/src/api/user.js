import request from "@/utils/request";

export function login(data) {
  return request({
    url: "/eviChain/user/login",
    method: "post",
    data
  });
}
