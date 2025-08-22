import Link from 'next/link'
 
export default function NotFound() {
  return (
      <main className="grid h-screen min-h-full place-items-center bg-gray-800 px-6 py-24 sm:py-32 lg:px-8">
        <div className="text-center">
          <p className="text-base font-semibold text-gray-500">404</p>
          <h1 className="mt-4 text-5xl font-semibold tracking-tight text-balance text-white sm:text-7xl">
            Página não encontrada
          </h1>
          <p className="mt-6 text-lg font-medium text-pretty text-gray-400 sm:text-xl/8">
            Desculpe, não conseguimos encontrar a página que você está procurando.
          </p>
          <div className="mt-10 flex items-center justify-center gap-x-6">
            <Link
            href="/"
              className="rounded-md bg-blue-700 px-3.5 py-2.5 text-sm font-semibold text-white shadow-xs hover:bg-blue-600 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500"
            >
              ← Voltar para o inicio
            </Link>
          </div>
        </div>
      </main>
  )
}