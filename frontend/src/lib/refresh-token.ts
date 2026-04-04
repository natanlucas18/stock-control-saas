"use client";

import { apiFetch } from "@/lib/api-client";
import { getApiUrl } from "@/lib/api-url";
import { ServerDTO } from "@/types/server-dto";
import { AuthResponse } from "@/types/user-schema";

const localhost = getApiUrl();
let isRefreshing = false;

export async function refreshSession() {
  if (isRefreshing) return;
  try {
    const res = await apiFetch<ServerDTO<AuthResponse>>(
      `${localhost}/auth/refresh`,
      {
        method: "POST",
      },
    );

    if (!res.success) {
      throw new Error("Failed to refresh session");
    }

    return res;
  } catch (err) {
    console.error("Erro ao renovar token:", err);
  } finally {
    isRefreshing = false;
  }
}
