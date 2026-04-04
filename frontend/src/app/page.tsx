import { useEffect } from "react"
import { useAuthStore } from "../stores/auth-store"
import HomePage from "./home/page"

export default function App() {
  const hydrateSession = useAuthStore((s) => s.hydrateSession)

  useEffect(() => {
    hydrateSession()
  }, [hydrateSession])

  return <HomePage />
}
