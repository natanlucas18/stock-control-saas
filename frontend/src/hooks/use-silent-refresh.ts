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
          expiresIn: res.data.tokenExpiresAt,
        });
      } catch (err) {
        console.error("Sessão expirada");
        logout(); 
      }
    }

    function schedule() {
      if(!expiresAt) return;
      const now = Date.now();
      const delay = expiresAt - now - 10_000;

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