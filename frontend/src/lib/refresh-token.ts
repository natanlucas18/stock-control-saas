"use client";

import { apiFetch } from "@/lib/api-client";
import { getApiUrl } from "@/lib/api-url";
import { ServerDTO } from "@/types/server-dto";
import { AuthResponse } from "@/types/user-schema";

const baseUrl = getApiUrl();

let refreshPromise: Promise<ServerDTO<AuthResponse>> | null = null;

export async function refreshSession(): Promise<ServerDTO<AuthResponse>> {
  if (refreshPromise) return refreshPromise;

  refreshPromise = (async () => {
    const res = await apiFetch<ServerDTO<AuthResponse>>(
      `${baseUrl}/auth/refresh`,
      { method: "POST" }
    );

    if (!res?.success || !res.data) {
      throw new Error("Falha ao renovar sessão");
    }

    return res;
  })();

  try {
    return await refreshPromise;
  } finally {
    refreshPromise = null;
  }
}