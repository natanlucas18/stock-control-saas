"use server"
import { PathLinks } from "@/types/path-links";
import { redirect } from "next/navigation";

let isRefreshing = false
let refreshPromise: Promise<void> | null = null

async function refreshToken() {
  await fetch("http://localhost:3000/auth/refresh", {
    method: "POST",
    credentials: "include",
  })
}

export async function apiFetch<T>(
  url: string,
  options?: RequestInit,
): Promise<T> {
  const makeRequest = () =>
    fetch(url, {
      ...options,
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
        ...(options?.headers || {}),
      },
    })

  let response = await makeRequest()

  if (response.status !== 401) {
    if (!response.ok) throw new Error(await response.text())
    return response.json()
  }

  try {
    if (!isRefreshing) {
      isRefreshing = true
      refreshPromise = refreshToken()
      await refreshPromise
      isRefreshing = false
    } else {
      await refreshPromise
    }

    response = await makeRequest()

    if (!response.ok) throw new Error(await response.text())

    return response.json()
  } catch {
    redirect(PathLinks.SIGN_IN)
  }
}
