"use client";

import { useEffect, useRef } from "react";
import { refreshSession } from "@/services/refresh-token-service";
import { useAuthStore } from "@/stores/auth-store";

export function useSilentRefresh() {
  const timerRef = useRef<ReturnType<typeof setTimeout> | null>(null);
  const expiresAt = useAuthStore((s) => s.expiresAt);

  useEffect(() => {
    if (!expiresAt) return;

    async function runRefresh() {
      try {
        const data = await refreshSession();
        useAuthStore.getState().setSession({
          user: {
            id: data.data.userId,
            name: data.data.userName,
            role: data.data.userRoles
          },
          expiresIn: data.data.expiresAt
        })
      } catch (err) {
        console.error("Session expired");
      }
    }

    function schedule() {
      if (!expiresAt) return;
      const now = Date.now();
      const delay = expiresAt - now - 10000;

      if (delay <= 0) {
        runRefresh();
        return;
      }

      timerRef.current = setTimeout(runRefresh, delay);
    }

    if (timerRef.current) {
      clearTimeout(timerRef.current);
    }

    schedule();

    return () => {
      if (timerRef.current) {
        clearTimeout(timerRef.current);
      }
    };
  }, [expiresAt]);
}
