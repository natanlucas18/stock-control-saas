"use client";

import { useEffect, useRef } from "react";
import { useAuthStore } from "@/stores/auth-store";
import { refreshSession } from "@/lib/refresh-token";

export function useSilentRefresh() {
  const timerRef = useRef<ReturnType<typeof setTimeout> | null>(null);
  const { expiresAt, setSession, logout } = useAuthStore();

  useEffect(() => {
    if (!expiresAt) return;

    async function runRefresh() {
      try {
        const res = await refreshSession();

        setSession({
          user: {
            id: res.data.userId,
            name: res.data.userName,
            role: res.data.userRoles,
          },
          expiresAt: new Date(res.data.tokenExpiresAt).getTime(),
        });

        schedule();
      } catch {
        console.error("Sessão expirada");
        logout();
      }
    }

    function schedule() {
      const { expiresAt } = useAuthStore.getState();

      if (!expiresAt) return;

      if (timerRef.current) {
        clearTimeout(timerRef.current);
      }

      const now = Date.now();
      const delay = expiresAt - now - 30_000;
      if (delay <= 0) {
        runRefresh();
        return;
      }

      timerRef.current = setTimeout(runRefresh, delay);
    }

    schedule();

    return () => {
      if (timerRef.current) {
        clearTimeout(timerRef.current);
      }
    };
  }, [expiresAt, setSession, logout]);
}