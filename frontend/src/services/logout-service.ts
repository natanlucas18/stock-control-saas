import { getApiUrl } from "@/lib/api-url"

const localhost = getApiUrl()
export async function logoutRequest() {
  await fetch(`${localhost}/api/auth/logout`, {
    method: "POST",
    credentials: "include",
  })
}
