"use client";

import { getApiUrl } from "./api-url";

/*export async function apiFetch<T>(
  url: string,
  options?: RequestInit,
): Promise<T> {
  const response = await fetch(url, {
    ...options,
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
      ...(options?.headers || {}),
    },
  });
  return response.json();
}*/

let isRefreshing = false
let refreshPromise: Promise<void> | null = null
const localhost = getApiUrl()

async function refreshToken() {
  await fetch(`${localhost}/auth/refresh`, {
    method: "POST",
    credentials: "include",
    headers: {
      'content-type': 'application/json'
    }
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
  } catch (error) {
    window.location.href = `/dashboard/login`
    throw error
  }
}


